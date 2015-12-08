package com.lnyp.stickylist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.listViewMusic)
    private SwipeMenuListView listViewMusic;

    @ViewInject(R.id.swipeRefreshMusic)
    private SwipeRefreshLayout swipeRefreshMusic;

    private List<Music> musics;

    private MusicAdapter musicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        musics = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            Music music = new Music();
            music.title = "上邪 : " + i;
            music.desc = "我欲与君相知，长命无绝衰";
            musics.add(music);
        }

        musicAdapter = new MusicAdapter(musics, this);
        listViewMusic.setAdapter(musicAdapter);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                editItem.setWidth(dp2px(90));
                // set item title
                editItem.setTitle("编辑");
                // set item title fontsize
                editItem.setTitleSize(18);
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(editItem);

                // create "edit" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.parseColor("#ff0000")));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listViewMusic.setMenuCreator(swipeMenuCreator);

        listViewMusic.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {// index即是操作menu添加的顺序
                    case 0:
                        Toast.makeText(MainActivity.this, "编辑 : " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "删除 : " + position, Toast.LENGTH_SHORT).show();
                        musics.remove(position);
                        musicAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        swipeRefreshMusic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshMusic.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 0; i < 4; i++) {

                            Music music = new Music();
                            music.title = "无缘 : " + i;
                            music.desc = "风雨千山玉独行，天下倾心叹无缘";
                            musics.add(music);
                        }
                        musicAdapter.notifyDataSetChanged();

                        swipeRefreshMusic.setRefreshing(false);
                    }
                }, 2000);

            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}