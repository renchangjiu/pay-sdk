package red.htt;

import org.junit.Test;
import red.htt.bean.wx.WxpayOrder;
import red.htt.bean.wx.NativePayRes;
import red.htt.service.WxpayService;

import java.util.UUID;

public class Demo {

    @Test
    public void mio() {
        WxpayConfigImpl config = new WxpayConfigImpl();
        WxpayService wxpayService = new WxpayService(config);
        WxpayOrder wxpayOrder = new WxpayOrder()
                .setId(UUID.randomUUID().toString().replaceAll("-", ""))
                // .setId("20200708141711316")
                .setSubject("test")
                .setBody("test")
                .setPrice("301");
        NativePayRes res = wxpayService.nativePay(wxpayOrder);
        System.out.println(res);
    }
}