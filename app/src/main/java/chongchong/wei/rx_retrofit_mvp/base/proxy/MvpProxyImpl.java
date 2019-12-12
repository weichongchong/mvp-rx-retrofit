package chongchong.wei.rx_retrofit_mvp.base.proxy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import chongchong.wei.rx_retrofit_mvp.base.BasePresenter;
import chongchong.wei.rx_retrofit_mvp.base.IBaseView;
import chongchong.wei.rx_retrofit_mvp.base.InjectPresenter;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.base.proxy
 * 创建人：apple
 * 创建时间：2019-12-12 17:52
 * 描述：
 */
public class MvpProxyImpl<V extends IBaseView> implements IMvpProxy {
    private V mView;
    private List<BasePresenter> mPresenters;

    public MvpProxyImpl(V view) {
        this.mView = view;
        mPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        //获得已经申明的变量，包括私有的
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attachView(mView);
                    field.setAccessible(true);
                    field.set(mView, mInjectPresenter);
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

    @Override
    public void unbindPresenter() {
        /**
         * 解绑，避免内存泄漏
         */
        for (BasePresenter presenter : mPresenters) {
            presenter.detachView();
        }
        mPresenters.clear();
        mPresenters = null;
    }

}

