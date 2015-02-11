package com.tcg.light.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.buffs.*;
import com.tcg.light.entities.enemies.*;

public class World {
	 
		private Array<Rectangle> bounds;
		private Array<Buff> buffs;
		private Array<Enemy> ens;
		
		private TiledMap tileMap;
		private OrthogonalTiledMapRenderer tmr;
		
		private float width, height, tileWidth, tileHeight;
		
		private float tileSize;
		
		public final int numLevels = 2;
		
		public World() throws LevelDoesNotExist {
			bounds = new Array<Rectangle>();
			buffs = new Array<Buff>();
			ens = new Array<Enemy>();
			if(Game.LEVEL <= numLevels) {
				createTiles();
			} else {
				throw new LevelDoesNotExist("There is no level " + Game.LEVEL);
			}
		}
		
		private void createTiles() {
			
			bounds.clear();
			buffs.clear();
			
			tileMap = new TmxMapLoader().load("maps/map" + Game.LEVEL + ".tmx");
			tmr = new OrthogonalTiledMapRenderer(tileMap);
			tileSize = tileMap.getProperties().get("tilewidth", Integer.class);
			
			TiledMapTileLayer ground;
			
			ground = (TiledMapTileLayer) tileMap.getLayers().get("ground");
			
			createLayer(ground, bounds);
			create1Up(buffs);
			createSmallAmmo(buffs);
			createSmallHealth(buffs);
			createFullAmmo(buffs);
			createFullHealth(buffs);
			resetEnemies();
			
			width = ground.getWidth() * tileSize;
			height = ground.getHeight() * tileSize;
			tileWidth = ground.getWidth();
			tileHeight = ground.getHeight();
			
			if(Game.LEVEL == 0) {
				Game.res.getMusic("level1").play();
			} else {
				Game.res.getMusic("level" + Game.LEVEL).play();
			}
		}
		
		public void newLevel(Terry t) throws LevelDoesNotExist {
			Game.res.stopMusic();
			t.setPosition(96, 32);
			Game.LEVEL++;
			if(Game.LEVEL <= numLevels) {
				createTiles();
			} else {
				throw new LevelDoesNotExist("There is no level " + Game.LEVEL);
			}
		}
		
		public void resetEnemies() {
			ens.clear();
			createSkeleton(ens);
			createImp(ens);
			createSlime(ens);
			createFlower(ens);
			createBoss(ens);
		}
		
		private void createLayer(TiledMapTileLayer layer, Array<Rectangle> rect) {
			
			for(int row = 0; row < layer.getHeight(); row++) {
				for(int col = 0; col < layer.getWidth(); col++) {
					
					Cell cell = layer.getCell(col, row);
					
					if(cell == null) continue;
					if(cell.getTile() == null) continue;
					
					rect.add(new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize));
					
				}
			}
			
		}
		
		private void create1Up(Array<Buff> buffs) {
			MapLayer up1;
			up1 = tileMap.getLayers().get("1up");
			for(MapObject mo : up1.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				buffs.add(new Stock(v));
			}
		}
		
		private void createSmallAmmo(Array<Buff> buffs) {
			MapLayer smallAmmo;
			smallAmmo = tileMap.getLayers().get("smallammo");
			for(MapObject mo : smallAmmo.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				buffs.add(new SmallAmmo(v));
			}
		}
		
		private void createSmallHealth(Array<Buff> buffs) {
			MapLayer smallHealth;
			smallHealth = tileMap.getLayers().get("smallhealth");
			for(MapObject mo : smallHealth.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				buffs.add(new SmallHealth(v));
			}
		}
		
		private void createFullAmmo(Array<Buff> buffs) {
			MapLayer fullAmmo;
			fullAmmo = tileMap.getLayers().get("fullammo");
			for(MapObject mo : fullAmmo.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				buffs.add(new FullAmmo(v));
			}
		}
		
		private void createFullHealth(Array<Buff> buffs) {
			MapLayer fullHealth;
			fullHealth = tileMap.getLayers().get("fullhealth");
			for(MapObject mo : fullHealth.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				buffs.add(new FullHealth(v));
			}
		}

		private void createSkeleton(Array<Enemy> en) {
			MapLayer skel;
			skel = tileMap.getLayers().get("skeleton");
			for(MapObject mo : skel.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				en.add(new Skeleton(v));
			}
		}
		
		private void createImp(Array<Enemy> en) {
			MapLayer imp;
			imp = tileMap.getLayers().get("imp");
			for(MapObject mo : imp.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				en.add(new Imp(v));
			}
		}
		
		private void createSlime(Array<Enemy> en) {
			MapLayer slime;
			slime = tileMap.getLayers().get("slime");
			for(MapObject mo : slime.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				en.add(new Slime(v));
			}
		}
		
		private void createFlower(Array<Enemy> en) {
			MapLayer flower;
			flower = tileMap.getLayers().get("flower");
			for(MapObject mo : flower.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				
				en.add(new Flower(v));
			}
		}
		
		private void createBoss(Array<Enemy> en) {
			MapLayer boss;
			boss = tileMap.getLayers().get("boss");
			for(MapObject mo : boss.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
				Vector2 v = new Vector2(e.x, e.y);
				if(Game.LEVEL == 0) {
					en.add(new Boss(v, 30));
				} else {
					en.add(new Boss(v));
				}
			}
		}
		
		@SuppressWarnings("unused")
		private void createObjectLayer(MapLayer layer, Array<Buff> buffs) {
			
			for(MapObject mo : layer.getObjects()) {
				Ellipse e = ((EllipseMapObject) mo).getEllipse();
			}
			
		}
		
		public void render(MyCamera cam, SpriteBatch sb) {
			tmr.setView(cam);
			tmr.render();
			
			sb.begin();
			sb.setProjectionMatrix(cam.combined);
			for(Buff b : buffs) {
				b.draw(null, sb, 0);
			}
			sb.end();
		}
		
		public void dispose() {
			for(Buff b: buffs) {
				b.dispose();
			}
			buffs.clear();
		}
		
		public Array<Rectangle> getBounds() {
			return bounds;
		}
	 
		public void setBounds(Array<Rectangle> bounds) {
			this.bounds = bounds;
		}
		
		public float getWidth() {
			return width;
		}

		public void setWidth(float width) {
			this.width = width;
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		public float getTileWidth() {
			return tileWidth;
		}

		public void setTileWidth(float tileWidth) {
			this.tileWidth = tileWidth;
		}

		public float getTileHeight() {
			return tileHeight;
		}

		public void setTileHeight(float tileHeight) {
			this.tileHeight = tileHeight;
		}

		public Array<Buff> getBuffs() {
			return buffs;
		}

		public void setBuffs(Array<Buff> buffs) {
			this.buffs = buffs;
		}

		public Array<Enemy> getEnemies() {
			return ens;
		}

		public void setEnemies(Array<Enemy> ens) {
			this.ens = ens;
		}
}
