package com.ghf.eshop.feature.category;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseFragment;
import com.ghf.eshop.network.EShopClient;
import com.ghf.eshop.network.core.UICallback;
import com.ghf.eshop.network.entity.category.CategoryPrimary;
import com.ghf.eshop.network.entity.category.CategoryRsp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import okhttp3.Call;
import okhttp3.Response;

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.standard_toolbar_title)
    TextView toolBarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_category)
    ListView listCategory;
    @BindView(R.id.list_children)
    ListView listChildren;

    private CategoryAdapter categoryAdapter;
    private ChildrenAdapter childrenAdapter;

    private List<CategoryPrimary> datas;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    protected int geContentViewLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {

        initToolbar();//处Toolbar
        categoryAdapter = new CategoryAdapter();
        listCategory.setAdapter(categoryAdapter);

        childrenAdapter = new ChildrenAdapter();
        listChildren.setAdapter(childrenAdapter);

        //拿到数据
        if (datas != null) {
            //直接更新UI
            updateCategory();

        } else {
            //进行网络请求拿到数据
            Call call = EShopClient.getInstance().getCategory();
            call.enqueue(new UICallback() {
                @Override
                public void onFailureInUI(Call call, IOException e) {
                    Toast.makeText(getContext(), "请求失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponseInUI(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        CategoryRsp categoryRsp = new Gson().fromJson(response.body().string(), CategoryRsp.class);
                        if (categoryRsp.getStatus().isSucceed()) {
                            datas = categoryRsp.getData();
                            //更新UI
                            updateCategory();
                        }

                    }
                }
            });
        }


    }

    /*
    * 更新分类数据
    * */
    private void updateCategory() {
        categoryAdapter.reset(datas);
        //切换展示二级分类
        chooseCategory(0);
    }

    /*
    * 根据一级分类选项展示二级分类内容
    * */
    private void chooseCategory(int position) {
        listCategory.setItemChecked(position, true);
        childrenAdapter.reset(categoryAdapter.getItem(position).getChildren());

    }

    @OnItemClick(R.id.list_category)
    public void OnItemClick(int position) {
        chooseCategory(position);
    }

    @OnItemClick(R.id.list_children)
    public void OnChildrenClick(int position) {
        String name = childrenAdapter.getItem(position).getName();
        // TODO: 2017/2/28 0028 完善到跳转页面
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
    }

    /*
    * 处理Toolbar
    * */
    private void initToolbar() {

        setHasOptionsMenu(true);//设置fragment有选项菜单
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置左上方的返回箭头
        toolBarTitle.setText(R.string.category_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        if (itemId == R.id.menu_search) {
            // TODO: 2017/2/28 0028 跳转到搜索 
            Toast.makeText(getContext(), "dianijispusuio", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
