package kr.ac.jbnu.inandout.manageyourself;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by rlwns on 2017-04-03.
 */

public class LoginActivity extends Activity {

    private EditText editTextpassword, editTextid;
    SoundPool soundPool;
    int sound;
    SharedPreferences checkMonth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivity(new Intent(this, SplashActivity.class));  //처음 게임 로딩 화면을 띄워준다.

        editTextid = (EditText) findViewById(R.id.editTextid);
        editTextpassword = (EditText) findViewById(R.id.editTextpassword);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound = soundPool.load(this, R.raw.button, 1);
        checkMonth = getSharedPreferences("checkMonth", Activity.MODE_PRIVATE);

    }

    public void gameStartButtonClicked(View view) {

        soundPool.play(sound, 1, 1, 0, 0, 1);

        // 아이디와 패스워드를 받아서 회원이 맞으면 회원정보를 넘겨주면서 다음 액티비티로 넘어간다.
        String id = editTextid.getText().toString();
        String password = editTextpassword.getText().toString();

        UserDBOpenHelper udbHelper = new UserDBOpenHelper(this);
        User user = new User("", "", "", "");


        if (udbHelper.checkUser(id, password, user)) {

            boolean checkInit = checkMonth.getBoolean(user.getId(), false);
            if (checkInit) {
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, SettingMonthActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        } else {                    // 회원 정보가 틀렸다면 다시 입력하게끔 알림과 초기화를 해준다.
            editTextid.setText("");
            editTextpassword.setText("");
            editTextid.requestFocus();
            Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
        }
    }

    // 아이디 찾기 화면으로 가기
    public void findIdButtonClicked(View view) {
        soundPool.play(sound, 1, 1, 0, 0, 1);
        Intent intent = new Intent(this, FindIdActivity.class);
        startActivity(intent);
    }

    // 비밀번호 찾기 화면으로 가기
    public void findPasswordButtonClicked(View view) {
        soundPool.play(sound, 1, 1, 0, 0, 1);
        Intent intent = new Intent(this, FindPasswordActivity.class);
        startActivity(intent);
    }

    // 회원가입 화면으로 가기
    public void registerButtonClicked(View view) {
        soundPool.play(sound, 1, 1, 0, 0, 1);
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
