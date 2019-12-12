package chongchong.wei.rx_retrofit_mvp.base.proxy;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.base.proxy
 * 创建人：apple
 * 创建时间：2019-12-12 17:51
 * 描述：定义代理的行为
 */
public interface IMvpProxy {
    void bindPresenter();

    void unbindPresenter();
}