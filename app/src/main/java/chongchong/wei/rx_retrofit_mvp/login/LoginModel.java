package chongchong.wei.rx_retrofit_mvp.login;

import android.os.Handler;
import android.view.View;

import chongchong.wei.rx_retrofit_mvp.ICallBack;


/**
 * 包名：chongchong.wei.rx_retrofit_mvp.login
 * 创建人：apple
 * 创建时间：2019-12-11 13:29
 * 描述：
 */
public class LoginModel implements LoginContract.IModel {
    @Override
    public void getUserInfo(String username, String pwd, final ICallBack callBack) {
        //todo 模拟 http请求
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callBack.success("成功");
            }
        };
        handler.postDelayed(runnable, 2000);

    }
}
