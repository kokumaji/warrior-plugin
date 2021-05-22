package com.dumbdogdiner.warrior.api.particle.shapes;

import com.dumbdogdiner.warrior.api.particle.Orientation;
import com.dumbdogdiner.warrior.api.particle.Parametric;
import com.dumbdogdiner.warrior.api.particle.ParticleSystem;
import com.dumbdogdiner.warrior.api.particle.Shape;
import org.bukkit.Particle;

/**
 * Draws a circle.
 */
public class Circle implements Shape {
    final double MAX_RADIUS = 250;

    private final double x;
    private final double y;
    private final double z;
    private final double r;

    private final Orientation orientation;
    private Parametric parametric;

    private Parametric XY = new Parametric() {
        @Override
        public double x(double t) {
            return Math.cos(t) * r + x;
        }

        @Override
        public double y(double t) {
            return Math.sin(t) * r + y;
        }

        @Override
        public double z(double t) {
            return z;
        }
    };

    private Parametric XZ = new Parametric() {
        @Override
        public double x(double t) {
            return Math.cos(t) * r + x;
        }

        @Override
        public double y(double t) {
            return y;
        }

        @Override
        public double z(double t) {
            return Math.sin(t) * r + z;
        }
    };

    private Parametric YZ = new Parametric() {
        @Override
        public double x(double t) {
            return x;
        }

        @Override
        public double y(double t) {
            return Math.cos(t) * r + y;
        }

        @Override
        public double z(double t) {
            return Math.sin(t) * r + z;
        }
    };

    /**
     * Construct a new circle with center (x,y,z) and radius r.
     */
    public Circle(double x, double y, double z, double r, Orientation orientation) {
        if (r > MAX_RADIUS) {
            throw new IllegalArgumentException("Tried to draw circle with absurd radius (>250)!");
        }
        
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.orientation = orientation;

        // set the orientation of the circle
        switch (this.orientation) {
            case XY: {
                this.parametric = this.XY;
                break;
            }
            case XZ: {
                this.parametric = this.XZ;
                break;
            }
            case YZ: {
                this.parametric = this.YZ;
                break;
            }
        }
    }

    @Override
    public <T> void draw(ParticleSystem system, Particle particle, T data) {
        system.parametric(
            particle,
            this.parametric,
            0,
            Math.PI * 2,
            Math.PI / (16*r),
            1,
            data
        );
    }

    @Override
    public <T> void drawAbsolute(ParticleSystem system, Particle particle, T data) {
        system.parametricAbsolute(
            particle,
            this.parametric,
            0,
            Math.PI * 2,
            Math.PI / (16*r),
            1,
            data
        );
    }
}
