package thwack;

import java.util.Map;

import collision.CollisionVisitor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Block implements Body, CollisionVisitor {
	
	final Rectangle rectangle;
	private Color color = Color.GREEN;
	
	public Block() {
		this(100, 100, 64, 64);
	}
	
	public Block(float x, float y, float width, float height) {
		this.rectangle = new Rectangle(x, y, width, height);
	}
	
	@Override
	public void update(float deltaTime, Map<String, Object> context) {
	}
	
	@Override
	public void render(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Filled);
		renderer.setColor(color);
		renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		renderer.end();
	}

	@Override
	public boolean collidesWith(CollisionVisitor visitor) {
		return this != visitor && visitor.visit(this.rectangle);
	}
	
	@Override
	public boolean visit(Circle circle) {
		return Intersector.overlaps(circle, rectangle);
	}
	
	@Override
	public boolean visit(Rectangle rect) {
		return Intersector.overlaps(this.rectangle, rect);
	}
}