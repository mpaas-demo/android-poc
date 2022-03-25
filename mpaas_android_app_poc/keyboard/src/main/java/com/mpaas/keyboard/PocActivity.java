package com.mpaas.keyboard;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.mobile.common.task.Log;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.mpaas.mas.adapter.api.MPTracker;
import com.mpaas.safekeyboard.api.IEncryptResultCallback;
import com.mpaas.safekeyboard.api.ISafeKeyboardVisibilityCallback;
import com.mpaas.safekeyboard.api.SafeKeyboard;
import com.mpaas.safekeyboard.view.SafeKeyboardEditText;
import com.mpaas.safekeyboard.view.SafeKeyboardView;

public class PocActivity extends BaseActivity implements ISafeKeyboardVisibilityCallback {
    SafeKeyboardEditText mEditText1, mEditText2, mEditText3;
    Button mBtnFinishEdit1, mBtnFinishEdit2, mBtnFinishEdit3, mBtnClear;
    SafeKeyboard mKeyBoard;
    IEncryptResultCallback mEdit1Callback, mEdit2Callback, mEdit3Callback;
    TextView mEncryptResult, mEncryptResultHint;
    SafeKeyboardView mKeyboardView;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        MPTracker.onActivityCreate(this);
        setContentView(R.layout.activity_poc);
        mKeyboardView = (SafeKeyboardView) findViewById(R.id.keyboard);
        mKeyboardView.setVisibilityCallback(this);

        mEncryptResult = (TextView) findViewById(R.id.tv_encrypt_result);
        mEncryptResultHint = (TextView) findViewById(R.id.tv_encrypt_result_hint);

        mBtnFinishEdit1 = (Button) findViewById(R.id.safe_edit1_finish);
        mBtnFinishEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyBoard.triggerGetEncryptOperation(mEditText1);
            }
        });

        mBtnFinishEdit2 = (Button) findViewById(R.id.safe_edit2_finish);
        mBtnFinishEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyBoard.triggerGetEncryptOperation(mEditText2);
            }
        });

        mBtnFinishEdit3 = (Button) findViewById(R.id.safe_edit3_finish);
        mBtnFinishEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyBoard.triggerGetEncryptOperation(mEditText3);
            }
        });

        mEdit1Callback = new IEncryptResultCallback() {
            @Override
            public void onFinishedInput(final String result) {
                Log.d(Constant.TAG, "mEdit1Callback onFinishedInput length:" + (result != null ? result.length() : 0));
//                try {
                if (result != null) {
                    //解密方法需要传入密文有效期值（单位是秒），设置为-1表示不对有效期进行校验。
                    //cipherText:加密后的内容
                    //Utils.RSA_PRI_KEY_1024：私钥
                    //128：私钥的字节数，私钥长度除以8
                    //-1：-1表示不对有效期进行校验，可以以秒为单位设置在有效期
//                        final String decryptResult = RSADecryptionInstance.get().decryptInput(result, Constant.RSA_PRIVATE_KEY_TEST_2048, 256, -1);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showEncryptResult(result, "rsa", result.length());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearEncryptResult();
                        }
                    });
                }
//                } catch (DecryptionException e) {
//                    e.printStackTrace();
//                }
            }
        };

        mEdit2Callback = new IEncryptResultCallback() {
            @Override
            public void onFinishedInput(final String result) {
                Log.d(Constant.TAG, "mEdit2Callback onFinishedInput length:" + (result != null ? result.length() : 0));

//                SM2DecryptionInstance sm2DecryptionInstance = new SM2DecryptionInstance();
//                try {
//                    sm2DecryptionInstance.init(Constant.SM2_PRIVATE_KEY_TEST);

                if (result != null) {
                    //解密方法需要传入密文有效期值（单位是秒），设置为-1表示不对有效期进行校验。
//                        final String decryptedStr = sm2DecryptionInstance.decryptInput(result, 10 * 60);
//                        System.out.println("sm2 decryption output content : " + decryptedStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showEncryptResult(result, "sm2", result.length());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearEncryptResult();
                        }
                    });
                }
//                } catch (PEMException e) {
//                    e.printStackTrace();
//                } catch (DecryptionException e) {
//                    e.printStackTrace();
//                }
            }
        };

        mEdit3Callback = new IEncryptResultCallback() {
            @Override
            public void onFinishedInput(final String result) {
                Log.d(Constant.TAG, "mEdit2Callback onFinishedInput length:" + (result != null ? result.length() : 0));

//                SM2DecryptionInstance sm2DecryptionInstance = new SM2DecryptionInstance();
//                try {
//                    sm2DecryptionInstance.init(Constant.SM2_PRIVATE_KEY_TEST);

                if (result != null) {
                    //解密方法需要传入密文有效期值（单位是秒），设置为-1表示不对有效期进行校验。
//                        final String decryptedStr = sm2DecryptionInstance.decryptInput(result, 10 * 60);
//                        System.out.println("sm2 decryption output content : " + decryptedStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showEncryptResult(result, "sm2", result.length());
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearEncryptResult();
                        }
                    });
                }
//                } catch (PEMException e) {
//                    e.printStackTrace();
//                } catch (DecryptionException e) {
//                    e.printStackTrace();
//                }
            }
        };

        mEditText1 = (SafeKeyboardEditText) findViewById(R.id.safe_edit1);
        mEditText1.initEncryptParam(Constant.RSA_PUBLIC_KEY_TEST_2048, mEdit1Callback);
        mEditText2 = (SafeKeyboardEditText) findViewById(R.id.safe_edit2);
        mEditText2.initEncryptParam(Constant.SM2_PUBLIC_KEY_TEST, mEdit2Callback);
        mEditText3 = (SafeKeyboardEditText) findViewById(R.id.safe_edit3);
        mEditText3.initEncryptParam(Constant.SM2_PUBLIC_KEY_TEST, mEdit3Callback);

        //TODO设置加密算法
        mKeyBoard = new SafeKeyboard(findViewById(R.id.root), mKeyboardView);
        mKeyBoard.monitorFocus(mEditText1.getId(), mEditText1);
        mKeyBoard.monitorFocus(mEditText2.getId(), mEditText2);
        mKeyBoard.monitorFocus(mEditText3.getId(), mEditText3);

        mBtnClear = (Button) findViewById(R.id.encrypt_result_clear);
        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyBoard.clear(mEditText1);
                mKeyBoard.clear(mEditText2);
                mKeyBoard.clear(mEditText3);
                clearEncryptResult();
            }
        });

        mEncryptResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", result);
                cm.setPrimaryClip(mClipData);
//                Toast.makeText(PocActivity.this, "加密结果已复制", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void clearEncryptResult() {
        showEncryptResult(null, null, 0);
    }

    private void showEncryptResult(String result, String algorithm, long length) {
        this.result = result;
        String hint = (String) getResources().getText(R.string.keyboard_ed_text_4);
        if (result == null) {
            result = "";
        } else {
            hint = hint + ": " + algorithm + ", " + length;
        }
        mEncryptResult.setText(result);
        mEncryptResultHint.setText(hint);
    }

    @Override
    public void onKeyboardVisibilityChanged(int i) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mKeyBoard.clear(mEditText1);
        mKeyBoard.clear(mEditText2);
        mKeyBoard.clear(mEditText3);
        MPTracker.onActivityDestroy(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        MPTracker.onActivityWindowFocusChanged(this, hasFocus);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MPTracker.onActivityResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MPTracker.onActivityPause(this);
    }

}