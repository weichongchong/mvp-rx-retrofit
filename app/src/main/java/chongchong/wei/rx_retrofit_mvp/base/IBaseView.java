package chongchong.wei.rx_retrofit_mvp.base;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.base
 * 创建人：apple
 * 创建时间：2019-12-11 15:42
 * 描述：
 */
public interface IBaseView {
    //请求网络显示等待框
    void showProgress(boolean isShow);

    //显示提示框-登录失败，或其他提示信息
    void showToast(String string);
}
