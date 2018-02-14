# Android MediaPlayer -- mp3
`Android` 里面提供了给我们一个可以操作多媒体文件（如影像和音乐）的类 --- `MediaPlayer`,为了熟悉当中的操作，我先从Mp3着手来测试了一下
## 一、`MediaPlayer`的状态管理
要使用 MediaPlayer 必须了解其对应的状态管理，否则很容易导致各种异常和错误。如图，这些就是一个多媒体文件播放前后可以经历到的所有状态了
![状态图](https://github.com/OuFungWah/IPCDemo/blob/master/app/MD/mediaplayer_state_diagram.png)

### 1、Idle（空闲态）
当MeidaPlayer刚刚被new出来的时候或者执行过 [reset()](https://developer.android.com/reference/android/media/MediaPlayer.html#reset()) 方法以后，MediaPlayer 即会停留在空闲态。处于空闲态的 MediaPlayer 不可以执行 [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start())。
* 执行[setDataSource()](https://developer.android.com/reference/android/media/MediaPlayer.html#setDataSource(java.lang.String))后会进入 Initialized state(初始态)
* 注意事项1：
   通过新建对象而进入的空闲态和通过 [reset()](https://developer.android.com/reference/android/media/MediaPlayer.html#reset()) 而进入的空闲态有些微妙的区别：<br/>在空闲态调用以下方法时：初创建的MediaPlayer不会触发错误，通过 [reset()](https://developer.android.com/reference/android/media/MediaPlayer.html#reset()) 而来的 MediaPlayer 则会触发错误
    
    * [getCurrentPosition()](https://developer.android.com/reference/android/media/MediaPlayer.html#getCurrentPosition())
    * [getDuration()](https://developer.android.com/reference/android/media/MediaPlayer.html#getDuration())
    * [getVideoHeight()](https://developer.android.com/reference/android/media/MediaPlayer.html#getDuration())
    * [getVideoWidth()](https://developer.android.com/reference/android/media/MediaPlayer.html#getVideoWidth())
    * [setAudioAttributes(AudioAttributes)](https://developer.android.com/reference/android/media/MediaPlayer.html#setAudioAttributes(android.media.AudioAttributes))
    * [setLooping(boolean)](https://developer.android.com/reference/android/media/MediaPlayer.html#setLooping(boolean))
    * [setVolume(float, float)]()
    * [pause()](https://developer.android.com/reference/android/media/MediaPlayer.html#pause())
    * [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start())
    * [stop()](https://developer.android.com/reference/android/media/MediaPlayer.html#stop())
    * [seekTo(long, int)]()
    * [prepare()](https://developer.android.com/reference/android/media/MediaPlayer.html#prepare())
    * [prepareAsync()](https://developer.android.com/reference/android/media/MediaPlayer.html#prepareAsync())
* 注意事项2：由create()得来的MediaPlayer对象直接就处于 Prepared state(准备态)

### 2、Initialized(初始态)
处于 Idle state(空闲态)的 MediaPlayer 经过调用 [setDataSource()](https://developer.android.com/reference/android/media/MediaPlayer.html#setDataSource(java.lang.String)) (也包括其他重载的 setDataSource 方法，此处为举例) 后即进入 Initialized state(初始态)
* 在除了在 Idle state(空闲态)以外的状态都不允许调用 [setDataSource()](https://developer.android.com/reference/android/media/MediaPlayer.html#setDataSource(java.lang.String)) 方法
* 在调用 [setDataSource()](https://developer.android.com/reference/android/media/MediaPlayer.html#setDataSource(java.lang.String)) 方法的时候记得捕获 IOException，减少程序崩溃的情况。

### 3、Preparing(准备中)
Preparing state(准备中)是一个短暂的中间态，所有 MediaPlayer 的操作方法都不可以在此状态中调用

### 4、Prepared(准备态)
MediaPlayer 必须先进入 Prepared state(准备态)才可以播放
* 进入 Prepared state(准备态)有以下两种方法
    * [prepare()](https://developer.android.com/reference/android/media/MediaPlayer.html#prepare()) (同步准备)
        调用后，当此方法执行完时，对应的 MediaPlayer 对象将变成 Prepared state(准备态)。在这之前还是它原来的状态。
    * [prepareAsync()](https://developer.android.com/reference/android/media/MediaPlayer.html#prepareAsync()) (异步准备)
        调用后，MediaPlayer对象立即进入 Preparing state(准备中态)，当 [prepareAsync()](https://developer.android.com/reference/android/media/MediaPlayer.html#prepareAsync()) 方法完成时，MediaPlayer 对象的状态再更新为 Prepared state(准备态)
* 当 MediaPlayer 对象进入 Prepared state(准备态),对应的多媒体文件的参数如(声音大小，循环等)都可以设置

### 5、Started(播放态)
MediaPlayer 对象调用 [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start()) 方法成功以后，多媒体文件就会开始播放，MediaPlayer 对象就会进入 Started state(开始态)。调用 isPlaying() 可判断是否在播放。
* 如果想监控 MediaPlayer 在系统底层的解码进度我们可以调用 setOnBufferingUpdateListener(OnBufferingUpdateListener) 方法注册一个监听器来监听进度
* 处于 Started state(播放态)时，无论多少次调用 [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start()) 都没有任何效果

### 6、Paused(暂停态)
调用 [pause()](https://developer.android.com/reference/android/media/MediaPlayer.html#pause()) 方法可以暂停多媒体文件的播放，使 MediaPlayer 对象进入 Paused state(暂停态)。**需要注意的是：从 Started state(开始态)到 Paused state(暂停态)的过渡其实是异步的，从调用方法到状态更新有可能会有时间差。**
* 在 Paused state(暂停态)时调用 [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start()) 可以继续播放，同时 MediaPlayer 即会从 Paused state(暂停态) 转为 Started state(播放态)
* 处于 Paused state(暂停态)时，无论多少次调用 [pause()](https://developer.android.com/reference/android/media/MediaPlayer.html#pause()) 都没有任何效果

### 7、Stopped(停止态)
调用 [stop()](https://developer.android.com/reference/android/media/MediaPlayer.html#stop()) 方法可将 MediaPlayer 从 Started state(播放态)、Paused state(暂停态)、Prepared state(准备态)和PlayBackCompleted state(播放完成态)转入 Stoped state(停止态)
* 处于 Stopped state(停止态)时，无论多少次调用 [stop()](https://developer.android.com/reference/android/media/MediaPlayer.html#stop()) 都没有任何效果

### 8、PlayBackCompleted(播放完成态)
当多媒体文件播放到结尾时，即为播放完成，进入 PlayBackCompeted state(播放完成态)。
* 如果 MediaPlayer 设置了 [setLooping(boolean)](https://developer.android.com/reference/android/media/MediaPlayer.html#setLooping(boolean)) 的话，当播放完成时，MediaPlayer 会自动重新播放文件并回到Started state(播放态)
* 如果没有设置循环，如果有注册 OnCompleteListener 则会调用 OnCompleteListener 的 onCompletion() 方法。
* 处于 PlayBackCompeted state(播放完成态)的 MediaPlayer 调用 [start()](https://developer.android.com/reference/android/media/MediaPlayer.html#start()) 方法即可重播这个多媒体文件

### 9、End(结束态)
当MediaPlayer的对象执行了[release()](https://developer.android.com/reference/android/media/MediaPlayer.html#release())后MediaPlayer的生命周期就进入End state(结束态)了，此后对应MediaPlayer对象就会在GC时被回收。
* 进入 End state(结束态)的 MediaPlayer 对象无法再回到其他状态
* ***注意：每个 MediaPlayer 使用完了，不再使用时以后千万记得要调用 [release()](https://developer.android.com/reference/android/media/MediaPlayer.html#release()) 释放掉对象，以防万一因占用系统资源而导致资源浪费***

### 10、Error(错误态)
一般情况下，MediaPlayer 会因为一些原因而导致执行操作失败，如：传入的多媒体文件格式不符；解析度过高；输入/出流超时。<br/>
错误的检测和修复至关重要。有时候由于编程上的失误或在错误的状态调用某些操作，如果有注册 OnErrorListener,则 MediaPlayer 的底层则会调用 OnErrorListener 的 onError() 方法。
* 只要是有错误的操作，不管有没有注册 onErrorListener ，MediaPlayer 都会进入 Error state(错误态)
* 为了重置进入错误态的 MediaPlayer ，我们可以调用 [reset()](https://developer.android.com/reference/android/media/MediaPlayer.html#reset()) 使错误态的 MediaPlayer 进入初始态
* 为了应用可以有更好的用户体验，建议注册 OnErrorListener 来应对 MediaPlayer 出现错误的情况

## 二、`MediaPlayer`对象的简单创建
最简单的操作应该就是想都不想直接播放就好了,如何实现呢
### 1、在 res 资源文件夹中创建 raw文件夹并把多媒体文件(音频或视频)放入
![raw文件夹](http://www.crazywah.com/md/android/picture/mp3_in_raw.png)
### 2、使用create()直接创建并播放
因为使用 create() 创建的 MediaPlayer 对象是直接进入准备态可以直接播放的，代码如下：
````java

public class MediaPlayerSimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer_activity_simple);

        //创建进入准备态的 MediaPlayer 对象，并直接绑定本地资源
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.always);
        //因为是直接使用Create方法，所以无需调用prepare();
        mediaPlayer.start();
    }
}

````
## 三、`MediaPlayer`对象的常规使用
### 1、创建一个 MediaPlayer 对象
```java
    
    //通过new关键字获得一个新的对象
    MediaPlayer mediaPlayer = new MediaPlayer();

```
### 2、设置多媒体文件的类型
```java
    //设置解压的多媒体类型
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
```
### 3、设置音频文件资源
#### 1）设置本地资源
前提是先将音频文件放入raw资源文件夹
```java
    //解析本地资源的Uri
    Uri localUri = Uri.parse(String.format("android.resource://%s/%s/%s",this.getPackageName(),"raw","always"));
    //调用setDataSource()方法设置本地资源Uri
    mediaPlayer.setDataSource(getApplicationContext(), localUri);
```
#### 2)设置网络资源
```java
    //定义好音频文件的完整网络位置
    String url = "http://other.web.rd01.sycdn.kuwo.cn/resource/n3/51/79/3854763359.mp3";
    //调用setDataSource()方法设置网络资源Url
    mediaPlayer.setDataSource(url);
```
### 4、进入准备态
```java
    //给mediaPlayer设置准备完毕监听器
    mediaPlayer.setOnPreparedListener(new OnPreparedListener(){
        @Override
            public void onPrepared(MediaPlayer mp) {
                //此处为准备完毕时执行的操作
            }
    });
    //在播放前需要让mediaPlayer进入准备态，异步准备
    mediaPlayer.prepareAsync()
```
### 5、播放
当MediaPlayer进入了准备态时即可开始播放了
```java

    //调用start()方法开始播放
    mediaPlayer.start();

```
### 6、其他操作
在必要的时候可以在适当的地方调用 pause()、stoped()等方法来对该播放过程进行控制，但是要注意MediaPlayer的状态转换。

## 4、播放进度控制
我们可以通过 seekTo() 方法来达到调整播放进度的目的，准备态，播放态，暂停态，停止态和完成态都可以执行该方法。
* 因为 seekTo() 方法是异步的方法，所以存在一个可能性就是 seekTo() 方法在调用后进度依旧没有调整完成。
为了避免出现逻辑错误，最好的解决方法就是注册一个 OnSeekCompeteListener 监听器，以确保在 seekTo() 方法完成以后才执行需要的操作。
* 可以通过 getCurrentPosition() 方法获取当前播放进度
### SeekBar 和 seekTo() 配合使用
使用SeekBar组件来实现最简单的进度调整是最适合的
```java

public class MainActivity extends android.support.v7.app.AppCompatActivity implements OnPreparedListener ,OnSeekCompleteListener{
    
    //进度条对象
    private SeekBar seekBar;
    //多媒体播放对象
    private MediaPlayer mediaPlayer;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.id.main_activity);
    
        //初始化对象
        seekBar = findViewById(R.id.seekbar);
        mediaPlayer = new MediaPlayer();
        
        //设置播放器准备态监听器
        mediaPlayer.setOnPreparedListener(this);
        //设置播放器进度调整监听器
        mediaPlayer.setOnSeekCompleteListener(this);
        //设置进度条变化监听器
        seekBar.setOnSeekBarChangeListener(this);
        
        //设置多媒体播放类型为MP3
        mediaPlayer.setAudioStreamType(AudioManager.STEAM_MUSIC);
        //调用异步准备方法
        mediaPlayer.prepareAsync();
        
        
    }
        
    @Override
    public void onPrepared(MediaPlayer mp) {
        //当多媒体播放器准备完成以后设置进度条的上限值
        seekBar.setMax(mp.getCurrentPosition());
        //准备完成以后立即播放
        mediaPlayer.start();
    }
    
    @Override
    public void onSeekComplete(MediaPlayer mp){
        //进度调整完成时的操作
        Toast.makeText(this, "当前进度为:"+mp.getCurrentPosition()/1000+"s", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //如果进度条的变化是来自用户的就执行进度调整的操作
        if (fromUser) {
            //调整播放进度
            mediaPlayer.seekTo(progress);
        }
    }
    
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        
    }
    
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    
    }
    
}

```