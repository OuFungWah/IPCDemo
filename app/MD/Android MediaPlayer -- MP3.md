# Android MediaPlayer -- mp3
`Android` 里面提供了给我们一个可以操作多媒体文件（如影像和音乐）的类 --- `MediaPlayer`,为了熟悉当中的操作，我先从Mp3着手来测试了一下
## `MediaPlayer`的状态管理
![状态图](mediaplayer_state_diagram.png)
如图，这些就是一个多媒体文件播放前后可以经历到的所有状态了
### 1、Idle（空闲态）
当MeidaPlayer刚刚被new出来的时候或者执行过 [reset()](https://developer.android.com/reference/android/media/MediaPlayer.html#reset()) 方法以后，MediaPlayer即会停留在空闲态。处于空闲态的MediaPlayer不可以执行start。
* 执行[setDataSource()](https://developer.android.com/reference/android/media/MediaPlayer.html#setDataSource(java.lang.String))后会进入Initialized(初始态)
* 注意事项1：
   通过新建对象而进入的空闲态和通过reset()而进入的空闲态有些微妙的区别：<br/>在空闲态调用以下方法时：初创建的MediaPlayer不会触发错误，通过reset()而来的MediaPlayer则会触发错误
    
    * getCurrentPosition()
    * getDuration()
    * getVideoHeight()
    * getVideoWidth()
    * setAudioAttributes(AudioAttributes)
    * setLooping(boolean)
    * setVolume(float, float)
    * pause()
    * start()
    * stop()
    * seekTo(long, int)
    * prepare()
    * prepareAsync()
* 注意事项2：由create()得来的MediaPlayer对象直接就处于Prepared state(准备态)

### 2、Initialized(初始态)

### 3、Preparing(准备中)
### 4、Prepared(准备态)
### 5、Started(播放态)
### 6、Paused(暂停态)
### 7、Stopped(停止态)
### 8、PlayBackCompleted(播放完成态)
### 9、End(结束态)
当MediaPlayer的对象执行了release()后MediaPlayer的生命周期就进入End state(结束态)了，此后对应MediaPlayer对象就会在GC时被回收。
* 进入End(结束态)的MediaPlayer对象无法再回到其他状态
* ***注意：每个MediaPlayer使用完了，不再使用时以后千万记得要调用release()释放掉对象，以防万一因占用系统资源而导致资源浪费***

### 10、Error(错误态)
一般情况下，MediaPlayer会因为

## `MediaPlayer`的简单使用
最简单的操作应该就是想都不想直接播放就好了,如何实现呢
