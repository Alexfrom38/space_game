package com.mygdx.game.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ForUnbeliver;
import com.mygdx.game.tools.Collision_Of_Objects;

public class Asteroid {

   public  int SPEED = 250;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    private static Texture texture;

    float x, y;
    Collision_Of_Objects rect;
    public boolean remove = false;

    public Asteroid (float x) {
        this.x = x;
        this.y = ForUnbeliver.HEIGHT;
        this.rect = new Collision_Of_Objects(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("asteroid.png");
    }

    public void update (float deltaTime) {

        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public Collision_Of_Objects getCollisionRect () {
        return rect;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

}