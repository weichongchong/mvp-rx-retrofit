package chongchong.wei.rx_retrofit_mvp.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.base
 * 创建人：apple
 * 创建时间：2019-12-11 16:30
 * 描述：
 */
public abstract class BaseMvpActivity extends AppCompatActivity {
    protected List<BasePresenter> mPresenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView;
        final Object layout = getLayout();
        if (layout instanceof Integer) {
            rootView = View.inflate(this, (int) getLayout(), null);
        } else if (layout instanceof View) {
            rootView = (View) getLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        setContentView(rootView);
        initPresenterByScanAnnotation();
        afterCreate();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (BasePresenter p : mPresenters) {
            p.detachView();
        }
        mPresenters.clear();
        mPresenters = null;
    }

    private void initPresenterByScanAnnotation() {
        mPresenters = new ArrayList<>();
        //获得已经申明的变量，包括私有的
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attachView(this);
                    field.setAccessible(true);
                    field.set(this, mInjectPresenter);
                    mPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                }
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    protected <T extends View> T $(@IdRes int viewId) {
        return findViewById(viewId);
    }

    public void showProgress(boolean isShow) {
        if (isShow) {
            Log.i("mvp_", "显示等待框");
        } else {
            Log.i("mvp_", "隐藏等待框");
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected abstract Object getLayout();

    protected abstract void afterCreate();
}
