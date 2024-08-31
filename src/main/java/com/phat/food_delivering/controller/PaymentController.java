package com.phat.food_delivering.controller;

import com.phat.food_delivering.config.VnpayConfig;
import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.OrderDTO;
import com.phat.food_delivering.dto.PaymentDTO;
import com.phat.food_delivering.dto.TransactionStatusDTO;
import com.phat.food_delivering.model.Order;
import com.phat.food_delivering.service.CartService;
import com.phat.food_delivering.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private CartService cartService;
    private OrderService orderService;

    @GetMapping("/create_payment")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                           @RequestHeader("Authorization") String token) throws UnsupportedEncodingException {
        CartDTO cartDTO = cartService.findCartByUserId(token);
        String orderType = "SUCCESS";
        long amount = cartDTO.totalPrice() * 100;

        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnpayConfig.getIpAddress(request);
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VnpayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VnpayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        vnp_Params.put("vnp_SecureHash", vnp_SecureHash);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setStatus("Ok");
        paymentDTO.setMessage("Successfully");
        paymentDTO.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentDTO);
    }

    @GetMapping("/payment_info")
    public ResponseEntity<?> transaction(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestHeader("Authorization") String token
    ) {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        OrderDTO orderDTO = orderService.getUserOrder(token).get(0);
        if (responseCode.equals("00")) {
            transactionStatusDTO.setStatus("OK");
            transactionStatusDTO.setMessage("Successfully!");
            transactionStatusDTO.setData("");
            orderService.updateOrderStatus(orderDTO.id(), "COMPLETED");
        } else {
            transactionStatusDTO.setStatus("No");
            transactionStatusDTO.setMessage("Failed!");
            transactionStatusDTO.setData("");
            orderService.cancelOrder(orderDTO.id());
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionStatusDTO);
    }
}
