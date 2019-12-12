package chongchong.wei.rx_retrofit_mvp.login;

import chongchong.wei.rx_retrofit_mvp.ICallBack;
import chongchong.wei.rx_retrofit_mvp.base.IBaseView;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.login
 * 创建人：apple
 * 创建时间：2019-12-11 13:27
 * 描述：
 */
public class LoginContract {
    /**
     * V 定义View的行为，调用者是P
     */
    interface IView extends IBaseView {
        //登录成功跳转到主界面
        void goToMainActivity();
    }

    /**
     * P（中转站和数据处理者） 定义View与Model的交互行为,调用者是V。P做中转
     */
    interface IPresenter {
        void onClickLogin(String username, String pwd);
    }

    /**
     * M 数据提供者
     */
    interface IModel {
        void getUserInfo(String username, String pwd, ICallBack callBack);
    }

}
