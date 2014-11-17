package thwack.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Rat extends Mob {

	public Body ratBody;
	private BodyDef ratBodyDef;
	private FixtureDef ratFixtureDef;
	private Direction direction = Direction.UP;
	private State state = State.BORED;

	public Vector2 velocity = new Vector2(0, 0).limit(getSpeed());

	public Rat(World world, Vector2 pos, Vector2 size) {
		super();
		this.setSpeed(10f);
		this.ratBodyDef = new BodyDef();
		this.ratBodyDef.type = BodyType.DynamicBody;
		this.ratBodyDef.position.set(pos);
		ratBody = world.createBody(ratBodyDef);
		ratBody.setUserData(this);
		ratBody.setFixedRotation(true);


		PolygonShape ratBodyShape = new PolygonShape();
		ratBodyShape.setAsBox(size.x, size.y);

		ratFixtureDef = new FixtureDef();
		ratFixtureDef.density = .5f;
		ratFixtureDef.shape = ratBodyShape;
		ratBody.createFixture(ratFixtureDef);
		ratFixtureDef.shape.dispose();

	}

	public void applyImpulse() {
		Vector2 targetVelocity = new Vector2(velocity).nor().scl(getSpeed());
		Vector2 impulse = new Vector2(targetVelocity);
		ratBody.setLinearVelocity(impulse);
	}

	public boolean isMoving() {
		// todo: get actual velocity from bx2d
		return true;
	}

	public void move(Vector2 velocity) {
		if (this.state == State.BORED) {
			this.velocity.set(0, 0);
		} else {
			this.velocity.set(velocity.nor());
			this.state = State.RUNNING;

			float pi = (float)Math.PI;
			float angle = velocity.getAngleRad();
			while(angle < 0) {
				angle += 2*pi;
			}
			angle = angle % (2*pi);

			if (angle > pi * 0.25 && angle < pi * 0.75) {
				this.direction = Direction.UP;
			} else if (angle > pi * 1.25 && angle < pi * 1.75) {
				this.direction = Direction.DOWN;
			} else if (angle > pi * 0.75 && angle < pi * 1.25) {
				this.direction = Direction.LEFT;
			} else {
				this.direction = Direction.RIGHT;
			}

			if (velocity.isZero(0.01f)) {
				this.velocity.set(0, 0);
				this.state = State.STANDING;
			}
		}
	}

	public void setState(State state) {
		if (this.state != state) {
			// reset the state time every time state changes
			stateTime = 0.0f;
		}
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setPosition(float f, float g) {
		this.position.set(f, g);
	}

	public Direction getDirection() {
		return direction;
	}

	public Vector2 getPosition() {
		return position;
	}
}
