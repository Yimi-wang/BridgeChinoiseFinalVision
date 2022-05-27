package Vue;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
/*
实现背景音乐的播放
有问题，不能用。暂时忽略。
 */
public class MusicTest {
    private static Clip bgm;//背景乐
    private static Clip hit;//音效
    private static AudioInputStream ais;
    MusicTest()
    { }
    public static void play(){
        try {
            bgm=AudioSystem.getClip();
            InputStream is=MusicTest.class.getClassLoader().getResourceAsStream("./bridgechinoise-New Version/bridgechinoise/res/music/背景音乐1.wav");
            //getclassLoader得到当前类的加载器.getResourceAsStream加载资源，只能加载wav的音乐格式
            if (is != null) {
                ais=AudioSystem.getAudioInputStream(is);//获取输入流
            }
            bgm.open(ais);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        bgm.start();//开始播放
        bgm.loop(Clip.LOOP_CONTINUOUSLY);//循环播放
    }
    public static void stop()
    {
        if(ais!=null)
            bgm.close();
    }
}
