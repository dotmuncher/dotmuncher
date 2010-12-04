package org.streetpacman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import android.view.View;

public class DMSpriteFactory {
	private Map<String, DMSprite> spritesMap = new HashMap<String, DMSprite>();
	private Map<Integer, Set<Integer>> animMap = new HashMap<Integer, Set<Integer>>();
	private Map<Integer,Integer> ctxMap = new HashMap<Integer,Integer>();
	public DMSprite getSprite(int animIndex, int ctx) {
		String key = animIndex + ":" + ctx;
		if (spritesMap.get(key) == null) {
			DMSprite dmSprite = new DMSprite(DMBoard.getInstance(), animIndex);
			spritesMap.put(key, dmSprite);
			DMBoard.getInstance().addSprite(dmSprite);
			DMBoard.getInstance().mapView.post(dmSprite.rAnimate);
			// add to animMap if not
			if (animMap.get(ctx) == null) {
				Set<Integer> animSet = new HashSet<Integer>();
				animMap.put(ctx, animSet);
			}
			animMap.get(ctx).add(animIndex);
		}
		onlyShowSprite(animIndex, ctx);
		ctxMap.put(ctx, animIndex);
		return spritesMap.get(key);
	}

	private void onlyShowSprite(int animIndex, int ctx) {
		String key = animIndex + ":" + ctx;
		spritesMap.get(key).setVisibility(View.VISIBLE);
		for (Integer i : animMap.get(ctx)) {
			if (i != animIndex) {
				spritesMap.get(i + ":" + ctx).setVisibility(View.GONE);
			}
		}
	}
	
	public int getAnimIndex(int ctx){
		if(ctxMap.get(ctx) == null){
			return 0;
		}
		return ctxMap.get(ctx);
	}
}
