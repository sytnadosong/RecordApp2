package com.sds.study.recordapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.attr.animation;

/**
 * Created by student on 2016-11-17.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{
    ListFragment listFragment;//대안폐기됨
    String filename;
    Button bt_play;
    TextView txt_filename;
    ImageView disc;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, null);
        txt_filename=(TextView)view.findViewById(R.id.txt_filename);
        bt_play=(Button)view.findViewById(R.id.bt_play);
        disc=(ImageView)view.findViewById(R.id.disc);
        /*페이지를 구성하는 나 아니 ㄴ다른 페이지 프레그먼트를 접근해보자
        */
        FragmentManager manager = this.getFragmentManager();
        List<Fragment> list = manager.getFragments();

        listFragment = (ListFragment) list.get(0);

        bt_play.setOnClickListener(this);
        Toast.makeText(getContext(), "filename : " + listFragment.filename, Toast.LENGTH_SHORT).show();

        return view;
    }

    /*방법2(방법1은 onItemChageListener사용 onSelected 메서드 재정의 및 활용)프래그먼트의
    * 생명주기를 활용하여 화면에 보여지기 시작할때 리스트 Fragment의 선택된 파일명을 접근해보자
    * 결론: 시점이 너무 빨라서 대안 폐기
    * */

   /* @Override
    public void onResume() {
        Toast.makeText(getContext(), "filename : " + listFragment.filename, Toast.LENGTH_SHORT).show();
    }*/
    public void play(){
        Animation anmation=AnimationUtils.loadAnimation(getContext(),R.anim.disc);
        /*안드로이드에서 애니메이션의 대상이 되는 주체는 모든 뷰이다.
        스크린 포함
        * */
        disc.startAnimation(anmation);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.bt_play){
            play();
        }
    }
}
