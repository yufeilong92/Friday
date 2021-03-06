package com.xuechuan.xcedu.ui.bank.newBankActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.bank.GmGridViewAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.GmReadFragment;
import com.xuechuan.xcedu.mvp.contract.GmSubmitComContract;
import com.xuechuan.xcedu.mvp.model.GmSubmitComModel;
import com.xuechuan.xcedu.mvp.presenter.GmSubmitComPresenter;
import com.xuechuan.xcedu.sqlitedb.CollectSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoBankSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.DoLogProgressSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.ErrorSqlteHelp;
import com.xuechuan.xcedu.sqlitedb.QuestionSqliteHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.EncryptionUtil;
import com.xuechuan.xcedu.utils.GmReadColorManger;
import com.xuechuan.xcedu.utils.GmTextUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.QuestionCaseVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.vo.SqliteVo.CollectSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoBankSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.DoLogProgreeSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.ErrorSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.QuestionSqliteVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ReaderViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: OrderTestActivity
 * @Package com.xuechuan.xcedu.ui.bank
 * @Description: 自由组卷
 * @author: L-BackPacker
 * @date: 2018.12.21 下午 4:06
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.12.21
 */
public class FreedomTestActivity extends BaseActivity implements View.OnClickListener, GmReadFragment.notification, GmSubmitComContract.View {
    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.modeexam.courserid";
    private static String PARAMT_LIST_DATA = "com.xuechuan.xcedu.ui.modeexam.list";

    private String mCourseId;
    private Context mContext;
    private GmTextUtil mTextUtil;
    private DoBankSqliteHelp mDoBankHelp;
    private DoLogProgressSqliteHelp mDoLogProgressSqliteHelp;
    private DialogUtil mDialogUtil;
    private ArrayList<QuestionCaseVo> lists;
    private GmReadFragment mReadFragment;
    private QuestionCaseVo mQuestionSqliteVo;
    private GmReadColorManger mColorManger;
    private QuestionSqliteHelp mSqliteHelp;
    private CommonPopupWindow mPopAnswer;
    private ImageView mIvTextBarBack;
    private ImageView mIvTextBarTimeImg;
    private TextView mActivityTitleText;
    private LinearLayout mLlTextBarTitle;
    private ImageView mIvTextBarDelect;
    private ImageView mIvTextBarMore;
    private LinearLayout mLlTextTitleBar;
    private View mVGmReadLine;
    private ReaderViewPager mReaderViewPager;
    private ImageView mShadowView;
    private FrameLayout mFlContentLayoutOne;
    private View mVGmBarLine;
    private TextView mTvTextCollect;
    private ImageView mIvTextMenu;
    private TextView mTvTextQid;
    private TextView mTvTextAllqid;
    private TextView mTvTextShare;
    private LinearLayout mLiTextNavbar;
    private LinearLayout mLlNewtextBar;
    private LinearLayout mModeexamLlLayout;
    private ArrayList<QuestionCaseVo> mlist;
    private TextView mTvEmpty;
    private ReaderViewPager mReaderViewPagertwo;
    private ImageView mIvGmbanJianpan;
    private EditText mEtGmSubmit;
    private ImageView mIvGmSubmitSend;
    private LinearLayout mLlGmSubmitEvalue;
    private AlertDialog mShowDialog;
    private GmSubmitComPresenter mSubmitPresenter;
    private ErrorSqlteHelp mErrorSqlteHelp;
    private CheckBox mChbGmCollect;
    private CollectSqliteHelp mCollectSqliteHelp;

    /**
     * @param context
     * @param courseid 科目
     */
    public static Intent start_Intent(Context context, String courseid, ArrayList<QuestionCaseVo> list) {
        Intent intent = new Intent(context, FreedomTestActivity.class);
        intent.putExtra(PARAMT_KEY, courseid);
        intent.putExtra(PARAMT_LIST_DATA, list);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_test);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exam_test);

        if (getIntent() != null) {
            mCourseId = getIntent().getStringExtra(PARAMT_KEY);
            mlist = (ArrayList<QuestionCaseVo>) getIntent().getSerializableExtra(PARAMT_LIST_DATA);
        }
        initView();
        initUtils();
        lists = mlist;
        //初始化翻页效果
        initReadViewPager();
        //显示下表
        initData(0);
        initEvent();
    }

    private void initEvent() {
        mChbGmCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!mChbGmCollect.isPressed()) {
                    return;
                }
                QuestionCaseVo mQuestionSqliteVo = lists.get(prePosition2);
                if (mQuestionSqliteVo != null) {
                    CollectSqliteVo vo = new CollectSqliteVo();
                    vo.setCollectable(isChecked ? 1 : 0);
                    vo.setChapterid(mQuestionSqliteVo.getChapter_id());
                    vo.setCourseid(Integer.parseInt(mCourseId));
                    vo.setQuestion_id(mQuestionSqliteVo.getQuestion_id());
                    vo.setQuestiontype(mQuestionSqliteVo.getQuestiontype());
                    if (isChecked) {
                        mCollectSqliteHelp.addCoolectItem(vo);
                        T.showToast(mContext, "收藏成功");
                    } else {
                        T.showToast(mContext, "取消收藏");
                        mCollectSqliteHelp.deleteItem(Integer.parseInt(mCourseId), mQuestionSqliteVo.getChapter_id(), mQuestionSqliteVo.getQuestion_id());
                    }
                } else {
                    T.showToast(mContext, "暂无题收藏");
                }
            }
        });

    }


    private void initUtils() {
        mTextUtil = GmTextUtil.get_Instance(mContext);
        mDoBankHelp = DoBankSqliteHelp.get_Instance(mContext);
        mDoLogProgressSqliteHelp = DoLogProgressSqliteHelp.get_Instance(mContext);
        mDialogUtil = DialogUtil.getInstance();
        mSqliteHelp = QuestionSqliteHelp.get_Instance(mContext);
        mErrorSqlteHelp = ErrorSqlteHelp.getInstance(mContext);
        mCollectSqliteHelp = CollectSqliteHelp.get_Instance(mContext);
        //提交评价
        mSubmitPresenter = new GmSubmitComPresenter();
        mSubmitPresenter.initModelView(new GmSubmitComModel(), this);
    }

    /*  public ArrayList<QuestionSqliteVo> getLists() {
          ArrayList<QuestionSqliteVo> list = mSqliteHelp.queryAllQuesitonWithCouresid(mCourseId);
          if (list != null && !list.isEmpty()) {
              for (int i = 0; i < list.size(); i++) {
                  QuestionSqliteVo vo = list.get(i);
                  String d = EncryptionUtil.D(vo.getQuestion());
                  vo.setQuestionStr(d);
                  String keyword = EncryptionUtil.D(vo.getKeywords());
                  vo.setKeywordStr(keyword);
                  String explain = EncryptionUtil.D(vo.getExplained());
                  vo.setExplainedStr(explain);
              }
          }
          return list;
      }
  */
    private void initData(int index) {
        mTvTextAllqid.setText(String.valueOf(lists.size()));
        mTvTextQid.setText(String.valueOf(++index));
    }
    /*
     */
    /**
     * 显示继续答题对话框
     *//*
    private void doShowDialogEvent() {
        if (mDoLogProgressSqliteHelp != null) {
            if (lists != null && !lists.isEmpty()) {
                final DoLogProgreeSqliteVo look = mDoLogProgressSqliteHelp.findLookWithTidChapterId(
                        lists.get(0).getChapter_id(), lists.get(0).getCourseid());
                if (look == null) return;
                mDialogUtil.showContinueDialog(mContext, look.getNumber());
                mDialogUtil.setContinueClickListener(new DialogUtil.onContincueClickListener() {
                    @Override
                    public void onCancelClickListener() {
                        if (mReaderViewPager != null) {
                            mReaderViewPager.setCurrentItem(0, true);
                            mDoLogProgressSqliteHelp.deleteLookItem(look.getId());
                        }
                    }

                    @Override
                    public void onNextClickListener() {
                        if (mReaderViewPager != null)
                            mReaderViewPager.setCurrentItem(Integer.parseInt(look.getNumber()) - 1, true);
                    }
                });
            }
        }

    }*/

    private int prePosition2;
    private int curPosition2;

    private void initReadViewPager() {
        mReaderViewPager.setAdapter(new GmFragmentAdpater(getSupportFragmentManager(), mContext, lists, mCourseId));
        mReaderViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mShadowView.setTranslationX(mReaderViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                prePosition2 = curPosition2;
                initData(position);
                setShowBarView(false);
                changerCollect();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mReaderViewPager.setOffscreenPageLimit(1);
    }

    private void initCollect(QuestionCaseVo vo) {
        CollectSqliteVo sqliteVo = mCollectSqliteHelp.queryCollectVo(Integer.parseInt(mCourseId),
                vo.getChapter_id(), vo.getQuestion_id());
        if (sqliteVo != null) {
            mChbGmCollect.setChecked(sqliteVo.getCollectable() == 1);
        } else {
            mChbGmCollect.setChecked(false);
        }
    }

    private void initView() {
        mContext = this;
        mIvTextBarBack = (ImageView) findViewById(R.id.iv_text_bar_back);
        mIvTextBarBack.setOnClickListener(this);
        mIvTextBarTimeImg = (ImageView) findViewById(R.id.iv_text_bar_time_img);
        mIvTextBarTimeImg.setOnClickListener(this);
        mActivityTitleText = (TextView) findViewById(R.id.activity_title_text);
        mActivityTitleText.setOnClickListener(this);
        mLlTextBarTitle = (LinearLayout) findViewById(R.id.ll_text_bar_title);
        mLlTextBarTitle.setOnClickListener(this);
        mIvTextBarDelect = (ImageView) findViewById(R.id.iv_text_bar_delect);
        mIvTextBarDelect.setOnClickListener(this);
        mIvTextBarMore = (ImageView) findViewById(R.id.iv_text_bar_more);
        mIvTextBarMore.setOnClickListener(this);
        mLlTextTitleBar = (LinearLayout) findViewById(R.id.ll_text_title_bar);
        mLlTextTitleBar.setOnClickListener(this);
        mVGmReadLine = (View) findViewById(R.id.v_gm_read_line);
        mVGmReadLine.setOnClickListener(this);
        mReaderViewPager = (ReaderViewPager) findViewById(R.id.readerViewPager);
        mReaderViewPager.setOnClickListener(this);
        mShadowView = (ImageView) findViewById(R.id.shadowView);
        mShadowView.setOnClickListener(this);
        mFlContentLayoutOne = (FrameLayout) findViewById(R.id.fl_content_layout_one);
        mFlContentLayoutOne.setOnClickListener(this);
        mVGmBarLine = (View) findViewById(R.id.v_gm_bar_line);
        mVGmBarLine.setOnClickListener(this);
        mTvTextCollect = (TextView) findViewById(R.id.tv_text_collect);
        mTvTextCollect.setOnClickListener(this);
        mIvTextMenu = (ImageView) findViewById(R.id.iv_text_menu);
        mIvTextMenu.setOnClickListener(this);
        mTvTextQid = (TextView) findViewById(R.id.tv_text_qid);
        mTvTextQid.setOnClickListener(this);
        mTvTextAllqid = (TextView) findViewById(R.id.tv_text_allqid);
        mTvTextAllqid.setOnClickListener(this);
        mTvTextShare = (TextView) findViewById(R.id.tv_text_share);
        mTvTextShare.setOnClickListener(this);
        mLiTextNavbar = (LinearLayout) findViewById(R.id.li_text_navbar);
        mLiTextNavbar.setOnClickListener(this);
        mLlNewtextBar = (LinearLayout) findViewById(R.id.ll_newtext_bar);
        mLlNewtextBar.setOnClickListener(this);
        mModeexamLlLayout = (LinearLayout) findViewById(R.id.modeexam_ll_layout);
        mModeexamLlLayout.setOnClickListener(this);
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mTvEmpty.setOnClickListener(this);
        mReaderViewPagertwo = (ReaderViewPager) findViewById(R.id.readerViewPagertwo);
        mReaderViewPagertwo.setOnClickListener(this);
        mIvGmbanJianpan = (ImageView) findViewById(R.id.iv_gmban_jianpan);
        mIvGmbanJianpan.setOnClickListener(this);
        mEtGmSubmit = (EditText) findViewById(R.id.et_gm_submit);
        mEtGmSubmit.setOnClickListener(this);
        mIvGmSubmitSend = (ImageView) findViewById(R.id.iv_gm_submit_send);
        mIvGmSubmitSend.setOnClickListener(this);
        mLlGmSubmitEvalue = (LinearLayout) findViewById(R.id.ll_gm_submit_evalue);
        mLlGmSubmitEvalue.setOnClickListener(this);
        mChbGmCollect = (CheckBox) findViewById(R.id.chb_gm_collect);
        mChbGmCollect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_text_menu://菜单
                showAnswerCardLayout();
                break;
            case R.id.iv_text_bar_more://更多
                if (mReadFragment != null) {
                    mReadFragment.showGmSetting();
                }
                break;
            case R.id.iv_text_bar_back://返回
                this.finish();
                break;
            case R.id.iv_gm_submit_send://评价
                String trim = mEtGmSubmit.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, getStringWithId(R.string.input_content));
                    return;
                }
                mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                mSubmitPresenter.submiteCommont(mContext,
                        String.valueOf(mQuestionSqliteVo.getParent_id()), trim, "", DataMessageVo.QUESTION);
                Utils.hideInputMethod(mContext, mEtGmSubmit);
                break;
            case R.id.iv_gmban_jianpan://键盘
                setShowBarView(false);
                break;
        }
    }

    /**
     * 设置答题卡布局
     */
    private void showAnswerCardLayout() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        mPopAnswer = new CommonPopupWindow(this, R.layout.pop_item_answer, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private Button mBtnSubmit;
            private TextView mTvPopNew;
            private TextView mTvLine;
            private TextView mTvTextPopERROR;
            private TextView mTvTextPopRight;
            private TextView mTvTextPopRegression;
            private TextView mTvPopCount;
            private RecyclerView mRlvPopContent;
            private GridView mGvPopContent;
            private LinearLayout mLLPopLayout;

            @Override
            protected void initView() {
                View view = getContentView();
                mTvPopNew = (TextView) view.findViewById(R.id.tv_pop_new);
                mTvLine = (TextView) view.findViewById(R.id.tv_line);
                mTvPopCount = (TextView) view.findViewById(R.id.tv_pop_count);
                mRlvPopContent = view.findViewById(R.id.rlv_pop_content);
                mGvPopContent = view.findViewById(R.id.gv_pop_content);
                mBtnSubmit = (Button) view.findViewById(R.id.btn_pop_answer_sumbit);
                mLLPopLayout = (LinearLayout) view.findViewById(R.id.ll_pop_layout);
                mTvTextPopRight = (TextView) view.findViewById(R.id.tv_text_pop_right);
                mTvTextPopERROR = (TextView) view.findViewById(R.id.tv_text_pop_error);
                mTvTextPopRegression = (TextView) view.findViewById(R.id.tv_text_pop_regression);
            }

            @Override
            protected void initEvent() {
                if (mColorManger != null) {
                    mTvPopNew.setTextColor(mColorManger.getmTextTitleColor());
                    mTvPopCount.setTextColor(mColorManger.getmTextTitleColor());
                    mBtnSubmit.setTextColor(mColorManger.getmTextTitleColor());
                    mLLPopLayout.setBackgroundColor(mColorManger.getmLayoutBgColor());
                    mTvTextPopRight.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopRegression.setTextColor(mColorManger.getmTextFuColor());
                    mTvTextPopERROR.setTextColor(mColorManger.getmTextFuColor());
                    mTvLine.setTextColor(mColorManger.getmTextTitleColor());
                }
                mTvPopNew.setText(String.valueOf(curPosition2 + 1));
                mTvPopCount.setText(String.valueOf(lists.size()));
                bindGridViewAdapter();
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        mTextUtil.setBackgroundAlpha(1f, FreedomTestActivity.this);
                    }
                });
            }

            private void bindGridViewAdapter() {
                GmGridViewAdapter adapter = new GmGridViewAdapter(mContext, lists);
                adapter.doEventListDatas(findAllDoDatas());
                adapter.doEventColor(mColorManger);
                adapter.doCurrentPostion(curPosition2);
                mGvPopContent.setAdapter(adapter);
                mGvPopContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        curPosition2 = position;
                        mReaderViewPager.setCurrentItem(position);
                        mPopAnswer.getPopupWindow().dismiss();
                    }
                });
            }
        };
        mPopAnswer.showAtLocation(mModeexamLlLayout, Gravity.BOTTOM, 0, 0);
        mTextUtil.setBackgroundAlpha(0.5f, FreedomTestActivity.this);
    }

    private ArrayList<DoBankSqliteVo> findAllDoDatas() {
        ArrayList<DoBankSqliteVo> list = new ArrayList<>();
        //查询用户数据
        ArrayList<DoBankSqliteVo> doBankSqliteVos = mDoBankHelp.finDAllUserDoText();
        if (doBankSqliteVos == null || doBankSqliteVos.isEmpty()) return list;
        for (int i = 0; i < lists.size(); i++) {
            QuestionCaseVo vo = lists.get(i);
            for (int k = 0; k < doBankSqliteVos.size(); k++) {
                DoBankSqliteVo sqliteVo = doBankSqliteVos.get(k);
                if (vo.getQuestion_id() == sqliteVo.getQuestion_id()) {
                    list.add(sqliteVo);
                }
            }
        }
        return list;
    }

    @Override
    public void SubmitSuccess(String success) {
        dismissDialog(mShowDialog);
        ResultVo vo = Utils.getGosnT(success, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                T.showToast(mContext, getStringWithId(R.string.evelua_sucee));
                setShowBarView(false);
            } else {
                T_ERROR(mContext);
            }

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void SubmiteError(String msg) {
        setShowBarView(false);
        T_ERROR(mContext);
    }


    public class GmFragmentAdpater extends FragmentStatePagerAdapter {

        private final String mCourseid;
        private Context mContext;
        private List<?> mListDatas;


        public GmFragmentAdpater(FragmentManager fm, Context mContext, List<?> mListDatas, String coursid) {
            super(fm);
            this.mListDatas = mListDatas;
            this.mContext = mContext;
            this.mCourseid = coursid;
        }

        @Override
        public Fragment getItem(int position) {
            if (mListDatas.size() == 0) {
                mReadFragment = GmReadFragment.newInstance(null, position, mCourseid);
            } else {
                mQuestionSqliteVo = (QuestionCaseVo) mListDatas.get(position);

                mReadFragment = GmReadFragment.newInstance(mQuestionSqliteVo, position, mCourseid);
            }
            return mReadFragment;
        }

        @Override
        public int getCount() {
            if (mListDatas.size() == 0) {
                return 1;
            } else
                return mListDatas.size();
        }
    }

    @Override
    public void saveUserDoLog(DoBankSqliteVo vo) {
        if (vo == null) return;
        mDoBankHelp.addDoBankItem(vo);

    }

    @Override
    public DoBankSqliteVo getUserDoLog(int quesiton_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(quesiton_id);
    }

    @Override
    public void deleteUserDolog(int quesiton_id) {
        mDoBankHelp.deleteBankWithQuestid(quesiton_id);
    }

    /**
     * 下一题
     */
    @Override
    public void doRightGo() {
        if (mReaderViewPager == null) return;
        int currentItem = mReaderViewPager.getCurrentItem();
        currentItem = currentItem + 1;
        if (currentItem < 0) {
            T.showToast(mContext, "已经是最后一题");
            currentItem = 0;
        }
        mReaderViewPager.setCurrentItem(currentItem, true);
    }

    @Override
    public void changerColor(GmReadColorManger colorManger) {
        this.mColorManger = colorManger;
        mLiTextNavbar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mTvTextCollect.setTextColor(colorManger.getmTextFuColor());
        mTvTextShare.setTextColor(colorManger.getmTextFuColor());
        mTvTextQid.setTextColor(colorManger.getmTextRedColor());
        mTvTextAllqid.setTextColor(colorManger.getmTextRedColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mActivityTitleText.setTextColor(colorManger.getmTextTitleColor());
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmReadLine.setBackgroundColor(colorManger.getmCutLineColor());
        //标题
        mLlTextTitleBar.setBackgroundColor(colorManger.getmLayoutBgColor());
        mVGmBarLine.setBackgroundColor(colorManger.getmCutLineColor());

    }

    @Override
    public QuestionSqliteVo getQuestionVo(int id) {
        QuestionSqliteVo vo = mSqliteHelp.queryQuestionVoWithId(id);
        String d = EncryptionUtil.D(vo.getQuestion());
        vo.setQuestionStr(d);
        String keyword = EncryptionUtil.D(vo.getKeywords());
        vo.setKeywordStr(keyword);
        String explain = EncryptionUtil.D(vo.getExplained());
        vo.setExplainedStr(explain);
        return vo;
    }


    @Override
    public void doSaveErrorLog(ErrorSqliteVo vo) {
        if (vo == null) return;
        mErrorSqlteHelp.addErrorItem(vo);
    }

    @Override
    public void upDataSaveErrorLog(ErrorSqliteVo vo) {

    }

    @Override
    public void changerBarView(boolean show) {
        setShowBarView(show);
    }

    @Override
    public boolean getErrorOrCollert() {
        return false;
    }

    @Override
    public void changerCollect() {
        QuestionCaseVo vo = lists.get(prePosition2);
        initCollect(vo);
    }

    /**
     * 是否显示输入框
     *
     * @param show
     */
    private void setShowBarView(boolean show) {
        mLiTextNavbar.setVisibility(show ? View.GONE : View.VISIBLE);
        mLlGmSubmitEvalue.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            Utils.showSoftInputFromWindow(FreedomTestActivity.this, mEtGmSubmit);
        } else {
            mEtGmSubmit.setText(null);
            Utils.hideInputMethod(mContext, mEtGmSubmit);
        }
    }


    @Override
    public DoBankSqliteVo queryUserData(int qustion_id) {
        if (mDoBankHelp == null) return null;
        return mDoBankHelp.queryWQid(qustion_id);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDoBankHelp != null) {
            mDoBankHelp.delelteTable();
        }
        doDoText();

    }

    private void doDoText() {
        if (curPosition2 == 0) return;
        if (mQuestionSqliteVo != null) {
            UserInfomDbHelp help = UserInfomDbHelp.get_Instance(mContext);
            UserInfomSqliteVo vo = help.findUserInfomVo();
            DoLogProgreeSqliteVo sqliteVo = new DoLogProgreeSqliteVo();
            sqliteVo.setChapterid(mQuestionSqliteVo.getChapter_id());
            sqliteVo.setKid(Integer.parseInt(mCourseId));
            sqliteVo.setTextid(mQuestionSqliteVo.getQuestion_id());
            sqliteVo.setNumber(String.valueOf(curPosition2 + 1));
            sqliteVo.setUserid(String.valueOf(vo.getSaffid()));
            sqliteVo.setAllNumber(String.valueOf(lists.size()));
            mDoLogProgressSqliteHelp.addDoLookItem(sqliteVo);
        }
    }

}
