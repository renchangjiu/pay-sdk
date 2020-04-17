package red.htt.service;

import red.htt.annotation.NonNull;
import red.htt.bean.NotifyRespVO;
import red.htt.bean.Order;
import org.apache.commons.lang3.tuple.Pair;
import red.htt.service.impl.AlipayServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * 统一收单下单并支付页面接口
 *
 * @author mio
 * @see "https://opendocs.alipay.com/apis/api_1/alipay.trade.page.pay"
 */
public interface AlipayService {

    /**
     * 请求 "统一收单下单并支付页面接口"
     *
     * @param bc     bc
     * @param config config
     * @return 自动提交的表单
     */
    @NonNull
    String pay(AlipayServiceImpl.BizContent bc, AlipayConfig config);

    /**
     * 支付宝同步跳转接口
     *
     * @param config  config
     * @param request request
     * @return pair's left: 是否通过验证, pair's right: 若通过验证, 则为orderId, 否则为空
     */
    Pair<Boolean, String> alipayReturn(AlipayConfig config, HttpServletRequest request);

    /**
     * 支付宝异步通知接口<br>
     * 本方法会验证 签名、商户ID、appId, 验证通过后返回订单ID, 调用方可对订单做进一步验证.<br>
     *
     * @param config  config
     * @param request request
     * @return NotifyRespVO
     */
    NotifyRespVO notify(AlipayConfig config, HttpServletRequest request);

}
