using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics.Animations.InterpolateFunctions {
    public static class InterpolateVector {
        private class Circuit {
            private float radius;
            private Func<float, float, float, float> interpolate;
            public Circuit(float radius) { this.radius = radius; interpolate = MathHelper.Lerp; }
            public Circuit(float radius, Func<float, float, float, float> interpolate) { this.radius = radius; this.interpolate = interpolate; }
            public void Interpolate(ref float from, ref float to, float amount, out Vector2 value) {
                float v = interpolate(from, to, amount);
                value.X = radius * (float)Math.Cos(v);
                value.Y = -radius * (float)Math.Sin(v);
            }
        }
        public static Interpolate<float, Vector2> InterpolateCircuit(float radius) {
            return (new Circuit(radius)).Interpolate;
        }
        public static Interpolate<float, Vector2> InterpolateCircuit(float radius, Func<float, float, float, float> interpolate) {
            return (new Circuit(radius, interpolate)).Interpolate;
        }
    }
}
