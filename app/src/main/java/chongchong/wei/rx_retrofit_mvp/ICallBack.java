package chongchong.wei.rx_retrofit_mvp;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.http
 * 创建人：apple
 * 创建时间：2019-12-11 14:35
 * 描述：网络数据请求结果的回调
 */
public interface ICallBack<T> {
    void success(T data);

    void error(String error);
}
