package com.example.abu.toolbardemo.wecome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.abu.toolbardemo.R;
import com.example.abu.toolbardemo.databinding.ActivityGuideBinding;
import com.example.abu.toolbardemo.wecome.adapter.GuideViewPaperAdapter;
import com.example.abu.toolbardemo.wecome.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuidActivity extends AppCompatActivity implements View.OnClickListener{
    ActivityGuideBinding mGuideBinding;;
    //创建ViewPaperAdapter对象
    private GuideViewPaperAdapter mAdapter;
    //创建ViewPaper容器所装载的views集合
    private List<View> mViews;
    //开始体验按钮
    private Button mstartBtn;

    //引导页view资源
    private static final int[] pics={R.layout.guide_view1,R.layout.guide_view2,R.layout.guide_view3};
    //引导页图片底部小圆点
    private  ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setExitTransition(new Slide(Gravity.RIGHT).setDuration(2000));
        getSupportActionBar().hide();
        mGuideBinding= DataBindingUtil.setContentView(this,R.layout.activity_guide);

        mViews=new ArrayList<>();

        //初始化引导页视图列表
        for (int i=0;i<pics.length;i++){
            View view= LayoutInflater.from(this).inflate(pics[i],null);

            if (i==pics.length-1){
                mstartBtn=view.findViewById(R.id.btn_enter);
                mstartBtn.setTag("enter");
                mstartBtn.setOnClickListener(this);
            }

            mViews.add(view);
        }

        mAdapter=new GuideViewPaperAdapter(mViews);
        mGuideBinding.vpGuide.setAdapter(mAdapter);
        mGuideBinding.vpGuide.addOnPageChangeListener(new PageChangeListener());

        initDots();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
       //如果切换到后台，就设置下次不进入功能引导页.
        SharedPreferencesUtil.putBoolean(WelcomeGuidActivity.this,SharedPreferencesUtil.FIRST_OPEN,false);
        finish();
    }

    private void initDots() {
        dots = new ImageView[pics.length];

        // 循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) mGuideBinding.ll.getChildAt(i);
            dots[i].setEnabled(false);// 都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选
    }
    /**
     * 设置当前view
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        mGuideBinding.vpGuide.setCurrentItem(position);
    }
    /**
     * 设置当前指示点
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void enterMainActivity() {
        Intent intent = new Intent(WelcomeGuidActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
        SharedPreferencesUtil.putBoolean(WelcomeGuidActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
        finish();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int position) {

        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

    }
}
