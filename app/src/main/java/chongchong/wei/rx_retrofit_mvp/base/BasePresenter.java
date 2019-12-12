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
    //看到很多博客这里使用软引用，其实没必要了，因为我们已经使用detachView 的方式处理了内存泄露问题了。这里看看就行，学习一下软引用的使用方式，没有实际意义
    private SoftReference<V> mViewReference;
    protected V mView;//mProxyView
    protected M mModel;

    protected void attachView(final V view) {
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

    protected void detachView() {
        mViewReference.clear();
        mViewReference = null;
        mView = null;
        mModel = null;
    }
}
