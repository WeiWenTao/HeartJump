package com.cucr.myapplication.fragment.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.fenTuan.FenTuanActivity;
import com.cucr.myapplication.activity.fuli.FuLiActiviry;
import com.cucr.myapplication.activity.home.SignActivity;
import com.cucr.myapplication.activity.huodong.HuoDongActivity;
import com.cucr.myapplication.activity.news.NewsActivity;
import com.cucr.myapplication.activity.photos.PhotoActivity;
import com.cucr.myapplication.activity.video.VideoActivity;
import com.cucr.myapplication.adapter.LvAdapter.HomeAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.temp.LocalImageHolderView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 911 on 2017/4/10.
 */

public class HomeFragment extends BaseFragment implements OnItemClickListener {

    private ListView mLv_home;
    private ConvenientBanner convenientBanner;
    private List<Integer> localImages = new ArrayList<>();
    private LinearLayout mLl_sign_in;
    private LinearLayout ll_fuli;
    private LinearLayout ll_active;
    private LinearLayout ll_fentuan;

    @Override
    protected void initView(View childView) {
        findView(childView);
        initLv();
    }


    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initLv() {
        //添加头部
        View headerView = View.inflate(mContext, R.layout.header_home_lv, null);

        //签到
        mLl_sign_in = (LinearLayout) headerView.findViewById(R.id.ll_sign_in);
        mLl_sign_in.setOnClickListener(this);

        //福利
        ll_fuli = (LinearLayout) headerView.findViewById(R.id.ll_fuli);
        ll_fuli.setOnClickListener(this);


        //活动
        ll_active = (LinearLayout) headerView.findViewById(R.id.ll_active);
        ll_active.setOnClickListener(this);

        //粉团
        ll_fentuan = (LinearLayout) headerView.findViewById(R.id.ll_fentuan);
        ll_fentuan.setOnClickListener(this);




        //首页轮播图
        convenientBanner = (ConvenientBanner) headerView.findViewById(R.id.convenientBanner);
        initARL();

        mLv_home.addHeaderView(headerView,null,true);
        mLv_home.addHeaderView(View.inflate(mContext,R.layout.header_home_lv_1,null),null,true);

        //去掉头部分割线
        mLv_home.setHeaderDividersEnabled(false);

        //用父类的Context
        mLv_home.setAdapter(new HomeAdapter(mContext));
        mLv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到评论和喜欢界面
//                startActivity(new Intent(mContext, CommentAndLikeActivity.class));
//                startActivity(new Intent(mContext, position  == 2 ? VideoActivity.class : NewsActivity.class));

                switch (position){
                    case 2:
                        startActivity(new Intent(mContext, VideoActivity.class));
                        break;

                    case 3:
                        startActivity(new Intent(mContext, NewsActivity.class));
                        break;

                    default:
                        startActivity(new Intent(mContext, PhotoActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){

            //签到
            case R.id.ll_sign_in:
                startActivity(new Intent(mContext,SignActivity.class));
                break;

            //福利
            case R.id.ll_fuli:
                startActivity(new Intent(mContext,FuLiActiviry.class));
                break;

            //粉团
            case R.id.ll_fentuan:
                startActivity(new Intent(mContext,FenTuanActivity.class));
                break;

            //活动
            case R.id.ll_active:
                startActivity(new Intent(mContext,HuoDongActivity.class));
                break;


        }

    }

    private void findView(View childView) {
        //首页ListView
        mLv_home = (ListView) childView.findViewById(R.id.lv_home);


    }

    //-------------------------------------------------------------------------------

    //初始化网络图片缓存库
    private void initImageLoader(){
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_launcher)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext.getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    /*
    加入测试Views
    * */
    private void initLocalImg() {
        //本地图片集合
        for (int position = 1; position < 3; position++)
            localImages.add(getResId("banner" + position  , R.drawable.class));

    }


    private void initARL(){
//        initImageLoader();
        initLocalImg();
        //本地图片例子
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.cricle_nor, R.drawable.icon_w_sel})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                .startTurning(3000)
                .setOnItemClickListener(this);

        convenientBanner.setManualPageable(true);//设置不能手动影响

        //网络加载例子
//        networkImages=Arrays.asList(images);
//        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//            @Override
//            public NetworkImageHolderView createHolder() {
//                return new NetworkImageHolderView();
//            }
//        },networkImages);



//手动New并且添加到ListView Header的例子
//        ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
//        mConvenientBanner.setMinimumHeight(500);
//        mConvenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnItemClickListener(this);
//        listView.addHeaderView(mConvenientBanner);
    }

    //---------------------------------------------------------------------------------


    //把布局传给父类
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_home;
    }


    //convenientBanner的点击监听
    @Override
    public void onItemClick(int position) {
        Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
    }


    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.setCanLoop(true);
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        // 停止自动翻页
        convenientBanner.setCanLoop(false);
    }
}
