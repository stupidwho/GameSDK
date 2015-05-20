package com.meizu.game.demo.mz;

import com.meizu.gamecenter.sdk.MzGameCenterPlatform;

import android.app.Application;

public class GameApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		// TODO 游戏初始化时，初始化参数
		MzGameCenterPlatform.init(this, GameConstants.GAME_ID, GameConstants.GAME_KEY);
	}

}
