using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics.Animations.InterpolateFunctions {
    public static class InterpolateFloat {
        public static void Lerp(ref float from, ref float to, float amount, out float value) {
            value = MathHelper.Lerp(from, to, amount);
        }
        public static void Clamp(ref float from, ref float to, float amount, out float value) {
            value = MathHelper.Clamp(amount, from, to);
        }
        public static void SmoothStep(ref float from, ref float to, float amount, out float value) {
            value = MathHelper.SmoothStep(from, to, amount);
        }
    }
}
