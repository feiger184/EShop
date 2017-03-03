package com.ghf.eshop.feature.category;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.ghf.eshop.R;
import com.ghf.eshop.base.BaseFragment;
import com.ghf.eshop.base.wrapper.ToastWrapper;
import com.ghf.eshop.base.wrapper.ToolbarWrapper;
import com.ghf.eshop.network.EShopClient;
import com.ghf.eshop.network.core.ApiPath;
import com.ghf.eshop.network.core.ResponseEntity;
import com.ghf.eshop.network.core.UICallback;
import com.ghf.eshop.network.entity.category.CategoryPrimary;
import com.ghf.eshop.network.entity.category.CategoryRsp;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

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
            UICallback uiCallback = new UICallback() {
                @Override
                public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {

                    if (isSucces) {
                        CategoryRsp categoryRsp = (CategoryRsp) responseEntity;
                        datas = categoryRsp.getData();

                        updateCategory();
                    }
                }
            };

            EShopClient.getInstance().enqueue(ApiPath.CATEGORY, null, CategoryRsp.class, uiCallback);
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
        ToastWrapper.show(name);
    }

    /*
    * 处理Toolbar
    * */
    private void initToolbar() {

        setHasOptionsMenu(true);//设置fragment有选项菜单

        new ToolbarWrapper(this).setCustomTitle(R.string.category_title);
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
            ToastWrapper.show("点击了搜索按钮");
        }
        return super.onOptionsItemSelected(item);
    }
}
