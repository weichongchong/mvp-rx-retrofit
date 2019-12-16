package chongchong.wei.rx_retrofit_mvp.base;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 包名：chongchong.wei.rx_retrofit_mvp.base
 * 创建人：apple
 * 创建时间：2019-12-11 15:43
 * 描述：
 */
public abstract class BasePresenter<V, M> {
    private SoftReference<V> mViewReference;
    protected V mView;//mProxyView
    protected M mModel;

    public void attachView(V view) {
        mViewReference = new SoftReference<V>(view);
//        mView = mViewReference.get();
        //AOP思想，统一做非空判断
        mView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mViewReference == null || mViewReference.get() == null) {
                    return null;
                }
                Object object = method.invoke(mViewReference.get(), args);
                return object;
            }
        });

        //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void detachView() {
        mViewReference.clear();
        mViewReference = null;
        mView = null;
        mModel = null;
    }
}
