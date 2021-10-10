package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.tools.Collision_Of_Objects;

public class EnemyBullet {
    public int SPEED = 700;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;
    private static Texture texture;

   public float x, y;
    Collision_Of_Objects rect;
    public boolean remove = false;

    public EnemyBullet (float x,float y) {
        this.x = x;
        this.y = y;
        this.rect = new Collision_Of_Objects(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("enemybullet.png");
    }

    public void update (float deltaTime) {
        y -= SPEED * deltaTime;
        if (y <0)
            remove = true;

        rect.move(x, y);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Collision_Of_Objects getCollisionRect () {
        return rect;
    }

}