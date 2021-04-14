package com.zxb.secai.ui.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zxb.secai.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private Button game_button_fhish,game_button_next;
    //存储图片资源的数组
    private int[] photos = {R.drawable.logo_ssss, R.drawable.logo_ssss, R.drawable.logo_yx
            , R.drawable.logo_sz, R.drawable.logo_qq, R.drawable.logo_bak};
    //显示当前图片的索引
    private int photoIndex = 0;
    //图片索引最大值（图片数-1）
    private int maxIndex = 5;
    private Bitmap bigBm;
    public static void startGameActivity(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        context.startActivity(intent);
    }

    /**
     * 当前动画是否正在执行
     */
    private boolean isAnimRun=false;
    /**
     *判断游戏是否开始*/
    private boolean isGameStart=false;
    /**
     *利用二维数组创建若干个游戏小方块
     */
    private ImageView[][] iv_game_arr = new ImageView[3][5];
    /**
     *游戏主界面
     */
    private GridLayout gl_main_game;
    /**
     *当前空方块的实例保存
     */
    private ImageView iv_null_ImageView;
    /**
     *当前手势
     */
    private GestureDetector mDetector;

    //非图片位置可以进行手势滑动
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event); //手势监听
    }

    //在图片上可以进行手势滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetector=new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            /**
             *一瞬间执行的方法
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
                int type=getDirByGes(e1.getX(),e1.getY(),e2.getX(),e2.getY());
                changeByDir(type);
                return false;
            }
        });
        setContentView(R.layout.activity_layout_game);
        game_button_fhish =findViewById(R.id.game_button_fhish);
//        game_button_next =findViewById(R.id.game_button_next);
        game_button_fhish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        game_button_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (photoIndex == maxIndex) {
//                    photoIndex = 0;
//                } else {
//                    //否则改为下一张图片索引
//                    photoIndex = photoIndex + 1;
//                }
//            }
//        });

        //初始化游戏的若干个小方块

//        Bitmap bigBm = BitmapFactory.decodeResource(getResources(), R.drawable.logo_bak);
        bigBm=((BitmapDrawable)getResources().getDrawable(photos[photoIndex])).getBitmap();
        int everyWidth=bigBm.getWidth()/5; //每个游戏小方块的宽和高
        for (int i = 0; i < iv_game_arr.length; i++) {
            for (int j = 0; j < iv_game_arr[0].length; j++) {
                Bitmap bm=Bitmap.createBitmap(bigBm,j*everyWidth,i*everyWidth,everyWidth,everyWidth);//根据行列来切成若干个游戏小图片
                iv_game_arr[i][j]=new ImageView(this);
                iv_game_arr[i][j].setImageBitmap(bm); //设置每一个游戏小方块图案
                iv_game_arr[i][j].setPadding(2,2,2,2);//设置方块之间的间距
                iv_game_arr[i][j].setTag(new GameData(i,j,bm)); //绑定自定义的数据
                iv_game_arr[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean flag=isHasByNullImageView((ImageView)view);
                        if(flag){
                            changeDataByImageView((ImageView)view);
                        }
                    }
                });
            }
        }
        //初始化游戏主界面，并添加若干个小方块
        gl_main_game = (GridLayout) findViewById(R.id.gl_main_game);
        for(int i=0;i<iv_game_arr.length;i++){
            for(int j=0;j<iv_game_arr[0].length;j++){
                gl_main_game.addView(iv_game_arr[i][j]);
            }
        }
        /**
         *设置最后一个方块为空的
         */
        setNullImageView(iv_game_arr[2][4]);
        /**
         *初始化随机打乱顺序
         */
        randomMove();
        isGameStart=true; //开始状态
    }



    public void changeByDir(int type){
        changeByDir(type,true);
    }

    /**
     * 根据手势的方向，获取空方块相应的相邻位置如果存在方块，那么进行数据交换
     * @param type 1：上，2：下，3：左，4：右
     * @param isAnim true:有动画，false:无动画
     */
    public void changeByDir(int type,boolean isAnim){
        /**
         *获取当前空方块的位置
         */
        GameData mNullGameData= (GameData) iv_null_ImageView.getTag();
        /**
         * 根据方向，设置相应的相邻的位置的坐标
         */
        int new_x=mNullGameData.x;
        int new_y=mNullGameData.y;
        if(type==1){ //要移动的方块在当前空方块的下边
            new_x++;
        }else if(type==2){ //要移动的方块在当前空方块的下边
            new_x--;
        }else if(type==3){ //要移动的方块在当前空方块的下边
            new_y++;
        }
        else if(type==4){ //要移动的方块在当前空方块的下边
            new_y--;
        }
        /**
         *判断这个新坐标，是否存在
         */
        if(new_x>=0&&new_x<iv_game_arr.length&&new_y>=0&&new_y<iv_game_arr[0].length){
            if(isAnim) {
                /**
                 *存在的话，开始移动
                 */
                changeDataByImageView(iv_game_arr[new_x][new_y]);
            }else{
                changeDataByImageView(iv_game_arr[new_x][new_y],isAnim);
            }
        }else{
            //什么也不做
        }
    }
    /**
     *判断游戏结束的方法
     */
    public void isGameOver(){
        boolean isGameOver=true;
        //要便利每个游戏小方块
        for(int i=0;i<iv_game_arr.length;i++){
            for(int j=0;j<iv_game_arr[0].length;j++){
                //为空的方块数据不判断跳过
                if(iv_game_arr[i][j]==iv_null_ImageView){
                    continue;
                }
                GameData mGameData= (GameData) iv_game_arr[i][j].getTag();
                if(!mGameData.isTrue()){
                    isGameOver=false;
                    break;
                }
            }
        }
        //根据一个开关变量决定游戏是否结束，结束时给提示
        if(isGameOver){
            Toast.makeText(this,"游戏结束",Toast.LENGTH_LONG).show();

        }
    }
    /**
     * 手势判断，是向左还是向右
     * @param start_x 手势的起始点x
     * @param start_y 手势的起始点y
     * @param end_x 手势的终止点x
     * @param end_y 手势的起始点y
     * @return 1：上，2：下，3：左，4：右
     */
    public int getDirByGes(float start_x,float start_y,float end_x,float end_y){
        boolean isLeftOrRight=(Math.abs(start_x-end_x)>Math.abs(start_y-end_y))?true:false; //是否左右
        if(isLeftOrRight){ //左右
            boolean isLeft=start_x-end_x>0?true:false;
            if(isLeft){
                return 3;
            }else{
                return 4;
            }
        }else{ //上下
            boolean isUp=start_y-end_y>0?true:false;
            if(isUp){
                return 1;
            }else{
                return 2;
            }
        }
    }

    /**
     * 随机打乱顺序
     */
    public void randomMove(){
        //打乱的次数
        for(int i=0;i<10;i++){
            //根据手势开始交换，无动画
            int type=(int)(Math.random()*4)+1;
            changeByDir(type,false);
        }
    }
    public void changeDataByImageView(final ImageView mImageView) {
        changeDataByImageView(mImageView,true);
    }

    /**
     * 利用动画结束之后，交换两个方块的数据
     * @param mImageView 点击的方块
     * @param isAnim true:有动画，false:无动画
     */
    public void changeDataByImageView(final ImageView mImageView,boolean isAnim){
        if(isAnimRun){ //如果动画已经开始，则不做交换操作
            return;
        }
        if(!isAnim){ //如果没有动画
            GameData mGameData= (GameData) mImageView.getTag();
            iv_null_ImageView.setImageBitmap(mGameData.bm);
            GameData mNullGameData= (GameData) iv_null_ImageView.getTag();
            mNullGameData.bm=mGameData.bm;
            mNullGameData.p_x=mGameData.p_x;
            mNullGameData.p_y=mGameData.p_y;
            setNullImageView(mImageView); //设置当前点击的是空方块
            if(isGameStart) {
                isGameOver(); //成功时谈一个toast
            }
            return;
        }
        /**
         *创建一个动画，设置好方向，移动的距离
         */
        TranslateAnimation translateAnimation = null;
        if(mImageView.getX()>iv_null_ImageView.getX()){ //当前点击的方块在空方块下边
            //往上移动
            translateAnimation=new TranslateAnimation(0.1f,-mImageView.getWidth(),0.1f,0.1f);
        }else if(mImageView.getX()<iv_null_ImageView.getX()){ //当前点击的方块在空方块下边
            //往下移动
            translateAnimation=new TranslateAnimation(0.1f,mImageView.getWidth(),0.1f,0.1f);
        }
        else if(mImageView.getX()>iv_null_ImageView.getY()){ //当前点击的方块在空方块下边
            //往左移动
            translateAnimation=new TranslateAnimation(0.1f,0.1f,0.1f,-mImageView.getWidth());
        }
        else if(mImageView.getX()<iv_null_ImageView.getY()){ //当前点击的方块在空方块下边
            //往右移动
            translateAnimation=new TranslateAnimation(0.1f,0.1f,0.1f,mImageView.getWidth());
        }
        /**
         * 设置动画的时长
         */
        translateAnimation.setDuration(70);
        /**
         * 设置动画结束之后是否停留
         */
        translateAnimation.setFillAfter(true);
        /**
         * 设置动画结束之后真正的交换数据
         */
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimRun=true; //动画开始
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimRun=false; //动画结束
                /**
                 *结束之后，清除动画
                 */
                mImageView.clearAnimation();
                GameData mGameData= (GameData) mImageView.getTag();
                iv_null_ImageView.setImageBitmap(mGameData.bm);
                GameData mNullGameData= (GameData) iv_null_ImageView.getTag();
                mNullGameData.bm=mGameData.bm;
                mNullGameData.p_x=mGameData.p_x;
                mNullGameData.p_y=mGameData.p_y;
                setNullImageView(mImageView); //设置当前点击的是空方块
                if(isGameStart) {
                    isGameOver(); //成功时谈一个toast
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        /**
         * 执行动画
         */
        mImageView.startAnimation(translateAnimation);
    }

    /**
     * 设置某个方块为空方块
     * @param mImageView 当前要设置为空的方块的实例
     */
    public void setNullImageView(ImageView mImageView){
        mImageView.setImageBitmap(null); //设置为空
        iv_null_ImageView=mImageView;
    }

    /**
     * 判断当前点击的方块，是否与空方块的位置关系是相邻关系
     * @param mImageView 所点击的方块
     * @return true：相邻；false：不相邻
     */
    public boolean isHasByNullImageView(ImageView mImageView){
        /**
         *分别获取当前空方块的位置与点击方块的位置，通过x，y两边都差1的方式判断
         */
        GameData mNullGameData= (GameData) iv_null_ImageView.getTag(); //空方块身上的数据
        GameData mGameData= (GameData)mImageView.getTag(); //点击方块身上的数据
        if(mNullGameData.y==mGameData.y&&mGameData.x+1==mNullGameData.x){ //当前点击的方块在空方块的上边
            return true;
        }else if(mNullGameData.y==mGameData.y&&mGameData.x-1==mNullGameData.x){ //当前点击的方块在空方块的下边
            return true;
        }else if(mNullGameData.y==mGameData.y+1&&mGameData.x==mNullGameData.x){ //当前点击的方块在空方块的左边
            return true;
        }else if(mNullGameData.y==mGameData.y-1&&mGameData.x+1==mNullGameData.x){ //当前点击的方块在空方块的右边
            return true;
        }
        return false;
    }

    /**
     * 每个游戏小方块上要绑定的数据
     */
    class GameData{
        /**
         *每个小方块的实际位置x
         */
        public int x=0;
        /**
         *每个小方块的实际位置x
         */
        public int y=0;
        /**
         *每个小方块的图片
         */
        public Bitmap bm;
        /**
         *每个小方块的图片的位置
         */
        public int p_x=0;
        /**
         *每个小方块的图片的位置
         */
        public int p_y=0;

        public GameData(int x, int y, Bitmap bm) {
            this.x = x;
            this.y = y;
            this.bm = bm;
            this.p_x = x;
            this.p_y = y;
        }

        /**
         * 每个小方块的位置是否正确
         * @return true:正确,false:不正确
         */
        public boolean isTrue() {
            if(x==p_x&&y==p_y) {
                return true;
            }
            return false;
        }
    }
}
