package com.web.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import com.web.utils.VNPayUtil;

import lombok.Getter;

@Configuration
public class VnpayConfig {
	@Getter
//    @Value("${payment.vnPay.url}")
    private String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
//    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl = "https://hcmutenoithat-backend.onrender.com/api/v1/payment/vn-pay-callback";
//    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode = "0OBXH4EM";
    @Getter
//    @Value("${payment.vnPay.secretKey}")
    private String secretKey = "M2V7ZZDBPQRIBRJSPL4TO25U42LVW9R6";
//    @Value("${payment.vnPay.version}")
    private String vnp_Version = "2.1.0";
//    @Value("${payment.vnPay.command}")
    private String vnp_Command = "pay";
//    @Value("${payment.vnPay.orderType}")
    private String orderType = "other";

    public Map<String, String> getVNPayConfig(String userId, String address, String description) {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef",  VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" +  VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl+"?userId="+userId+"&address="+address+"&description="+description);
//        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        return vnpParamsMap;
    }
}
