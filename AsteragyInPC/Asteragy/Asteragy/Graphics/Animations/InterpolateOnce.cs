using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics.Animations
{
    public delegate void Interpolate<T>(ref T from, ref T to, float amount, out T value);
    public class InterpolateOnce<T> : IUpdate
    {
        private TimeSpan time = TimeSpan.Zero;
        private Interpolate<T> interpolate;
        private T from;
        private T to;
        private T now;

        public TimeSpan Unit { get; set; }
        public T From { get { return from; } set { from = value; } }
        public T To { get { return to; } set { to = value; } }
        public T Now { get { return now; } }
        public bool End { get { return time >= Unit; } }

        #region InterpolateOnce
        public InterpolateOnce(Interpolate<T> interpolate)
        {
            this.interpolate = interpolate;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time, T from, T to)
            : this(interpolate)
        {
            Unit = time;
            From = from;
            To = to;
        }
        #endregion

        #region Restart
        public void Restart()
        {
            time = TimeSpan.Zero;
        }
        public void Restart(TimeSpan unit)
        {
            Unit = unit;
            Restart();
        }
        public void Restart(T from, T to)
        {
            From = from;
            To = to;
            Restart();
        }
        public void Restart(TimeSpan unit, T from, T to)
        {
            Unit = unit;
            From = from;
            To = to;
            Restart();
        }
        #endregion

        #region IUpdate メンバ

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            if (time < Unit)
            {
                interpolate(ref from, ref to, (float)(time.TotalMilliseconds / Unit.TotalMilliseconds), out now);
                time += gameTime.ElapsedGameTime;
            }
        }

        #endregion
    }
}
