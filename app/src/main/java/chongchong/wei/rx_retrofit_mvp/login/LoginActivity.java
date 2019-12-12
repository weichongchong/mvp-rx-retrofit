package chongchong.wei.rx_retrofit_mvp.login;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chongchong.wei.rx_retrofit_mvp.R;
import chongchong.wei.rx_retrofit_mvp.base.BaseMvpActivity;
import chongchong.wei.rx_retrofit_mvp.base.BasePresenter;
import chongchong.wei.rx_retrofit_mvp.base.InjectPresenter;

public class LoginActivity extends BaseMvpActivity implements LoginContract.IView {
    @InjectPresenter
    private LoginPresenter loginPresenter;

    @Override
    protected Object getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void afterCreate() {
        $(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.onClickLogin("xxx", "xxx");
            }
        });
    }

    @Override
    public void goToMainActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //todo 登录成功跳转到主界面
                Log.i("mvp_", "登录成功跳转到主界面");
            }
        });

    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            Log.i("mvp_", "显示等待框");
        } else {
            Log.i("mvp_", "隐藏等待框");
        }
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
