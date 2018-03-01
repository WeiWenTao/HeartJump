package com.cucr.myapplication.core.chat;

import android.content.Context;

import com.cucr.myapplication.app.MyApplication;
import com.cucr.myapplication.listener.LoadChatServer;
import com.cucr.myapplication.utils.MyLogger;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by cucr on 2018/2/28.
 */

public class ChatCore {

    private Context context;

    public void connect(String token, final LoadChatServer loadChatServer) {
        context = MyApplication.getInstance();
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 *
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    MyLogger.jLog().i("成功:" + userid);
                    loadChatServer.onLoadSuccess(userid);
                }

                /**
                 * 连接融云失败
                 *
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    MyLogger.jLog().i("失败:" + errorCode);
                    loadChatServer.onLoadFial(errorCode);
                }
            });
        }
    }
}
