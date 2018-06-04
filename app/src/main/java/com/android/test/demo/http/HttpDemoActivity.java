package com.android.test.demo.http;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.test.demo.R;
import com.android.test.demo.http.bean.AddressInfo;
import com.android.test.demo.http.bean.Contributor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "TestRetrofit";
    private Button mBtnTestGithub, mBtnTestSojson;
    private Button mBtnTestRxJava, mBtnTestJava8;

    private Button mBtnTestBitmap;
    private ImageView mBitmapView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_http);

        mBtnTestGithub = (Button) findViewById(R.id.id_btn_test_github);
        mBtnTestGithub.setOnClickListener(this);

        mBtnTestSojson = (Button) findViewById(R.id.id_btn_test_sojson);
        mBtnTestSojson.setOnClickListener(this);

        mBtnTestRxJava = (Button) findViewById(R.id.id_btn_test_rxjava);
        mBtnTestRxJava.setOnClickListener(this);

        mBtnTestJava8 = (Button) findViewById(R.id.id_btn_test_java8);
        mBtnTestJava8.setOnClickListener(this);

        mBtnTestBitmap = (Button) findViewById(R.id.id_btn_test_bitmap);
        mBtnTestBitmap.setOnClickListener(this);

        mBitmapView = (ImageView) findViewById(R.id.id_bitmap_view);

    }

    @Override
    public void onClick(View v) {
        if (v == mBtnTestGithub) {
            cacheOfServer();
        } else if (v == mBtnTestSojson) {
            cacheOfClinet();
        } else if (v == mBtnTestRxJava) {
            requestOfRxJava();
        } else if (v == mBtnTestJava8) {
            requestOfJava8();
        } else if (v == mBtnTestBitmap) {
            requestBitmap();
        }
    }

    private void cacheOfServer() {
        Call<List<Contributor>> call =  HttpFactory.getGitHubHttpInterface()
                .contributors("square", "retrofit");
        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                printContributors(response.body());
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                Log.i(TAG, "onFailure()", t);
            }
        });
    }

    private void cacheOfClinet() {
        Call<AddressInfo> call = HttpFactory.getSinaHttpInterfaces()
                .requestAddressInfo();
        call.enqueue(new Callback<AddressInfo>() {
            @Override
            public void onResponse(Call<AddressInfo> call, Response<AddressInfo> response) {
                AddressInfo info = response.body();
                Log.i(TAG, "city: " + info.getCity() + ", province: " + info.getProvince());
            }

            @Override
            public void onFailure(Call<AddressInfo> call, Throwable t) {

            }
        });
    }

    private void requestOfRxJava() {
        Observable<List<Contributor>> observable = HttpFactory.getGitHubHttpInterface()
                .contributorsOfRxJava("square", "retrofit");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Contributor> value) {
                        printContributors(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
        });

    }

    private void requestOfJava8() {
        CompletableFuture<List<Contributor>> future =  HttpFactory.getGitHubHttpInterface()
                .contributorsOfJava8("square", "retrofit");

        future.thenAccept(new Consumer<List<Contributor>>() {
            @Override
            public void accept(List<Contributor> contributors) {
                printContributors(contributors);
            }
        });
    }

    private void requestBitmap() {
        Call<Bitmap> call = HttpFactory.getBitmapInterfaces().getBitmap1();
        call.enqueue(new Callback<Bitmap>() {
            @Override
            public void onResponse(Call<Bitmap> call, Response<Bitmap> response) {
                mBitmapView.setImageBitmap(response.body());
            }

            @Override
            public void onFailure(Call<Bitmap> call, Throwable t) {

            }
        });
    }

    private void printContributors (List<Contributor> contributors) {
        for (Contributor contributor : contributors) {
            Log.i(TAG, contributor.login + " (" + contributor.contributions + ")");
        }
    }
}
