using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace Asteragy.Graphics.Animations {
    public delegate void Interpolate<T>(ref T from, ref T to, float amount, out T value);
    public class InterpolateOnce<T> : IInterpolate {
        private TimeSpan time = TimeSpan.Zero;
        private Interpolate<T> interpolate;
        private T from;
        private T to;
        private T now;
        private bool end = false;

        public TimeSpan Unit { get; set; }
        public T From { get { return from; } set { from = value; } }
        public T To { get { return to; } set { to = value; } }
        public T Now { get { return now; } }
        public bool End { get { return end; } }

        public event Action Ended = delegate { };

        #region InterpolateOnce
        public InterpolateOnce(Interpolate<T> interpolate) {
            this.interpolate = interpolate;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time)
            : this(interpolate) {
            Unit = time;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time, TimeSpan now)
            : this(interpolate, time) {
            this.time = now;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time, T from, T to)
            : this(interpolate, time) {
            this.from = from;
            this.to = to;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time, TimeSpan now, T from, T to)
            : this(interpolate, time, now) {
            this.from = from;
            this.to = to;
        }
        public InterpolateOnce(Interpolate<T> interpolate, TimeSpan time, T start, TimeSpan now, T from, T to)
            : this(interpolate, time, now, from, to) {
            this.now = start;
        }
        #endregion

        #region Restart
        public void Restart() {
            time = TimeSpan.Zero;
            end = false;
        }
        public void Restart(TimeSpan unit) {
            Unit = unit;
            Restart();
        }
        public void Restart(T from, T to) {
            From = from;
            To = to;
            Restart();
        }
        public void Restart(TimeSpan unit, T from, T to) {
            Unit = unit;
            From = from;
            To = to;
            Restart();
        }
        #endregion

        #region IUpdate メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            if(time < Unit) {
                interpolate(ref from, ref to, (float)(time.TotalMilliseconds / Unit.TotalMilliseconds), out now);
                time += gameTime.ElapsedGameTime;
            } else if(!end) {
                interpolate(ref from, ref to, 1.0f, out now);
                end = true;
                Ended();
            }
        }

        #endregion
    }
    public delegate void Interpolate<T, U>(ref T from, ref T to, float amount, out U value);
    public class InterpolateOnce<T, U> : IInterpolate {
        private TimeSpan time = TimeSpan.Zero;
        private Interpolate<T, U> interpolate;
        private T from;
        private T to;
        private U now;
        private bool end = false;

        public TimeSpan Unit { get; set; }
        public T From { get { return from; } set { from = value; } }
        public T To { get { return to; } set { to = value; } }
        public U Now { get { return now; } }
        public bool End { get { return time >= Unit; } }

        public event Action Ended = delegate { };

        #region InterpolateOnce
        public InterpolateOnce(Interpolate<T, U> interpolate) {
            this.interpolate = interpolate;
        }
        public InterpolateOnce(Interpolate<T, U> interpolate, TimeSpan time)
            : this(interpolate) {
            Unit = time;
        }
        public InterpolateOnce(Interpolate<T, U> interpolate, TimeSpan time, TimeSpan now)
            : this(interpolate, time) {
            this.time = now;
        }
        public InterpolateOnce(Interpolate<T, U> interpolate, TimeSpan time, T from, T to)
            : this(interpolate, time) {
            this.from = from;
            this.to = to;
        }
        public InterpolateOnce(Interpolate<T, U> interpolate, TimeSpan time, TimeSpan now, T from, T to)
            : this(interpolate, time, now) {
            this.from = from;
            this.to = to;
        }
        public InterpolateOnce(Interpolate<T, U> interpolate, TimeSpan time, U start, TimeSpan now, T from, T to)
            : this(interpolate, time, now, from, to) {
            this.now = start;
        }
        #endregion

        #region IRestartable メンバ
        public void Restart() {
            time = TimeSpan.Zero;
            end = false;
        }
        public void Restart(TimeSpan unit) {
            Unit = unit;
            Restart();
        }
        public void Restart(T from, T to) {
            From = from;
            To = to;
            Restart();
        }
        public void Restart(TimeSpan unit, T from, T to) {
            Unit = unit;
            From = from;
            To = to;
            Restart();
        }
        #endregion

        #region IUpdate メンバ

        public void Update(GraphicsDevice device, GameTime gameTime) {
            if(time < Unit) {
                interpolate(ref from, ref to, (float)(time.TotalMilliseconds / Unit.TotalMilliseconds), out now);
                time += gameTime.ElapsedGameTime;
            } else if(!end) {
                interpolate(ref from, ref to, 1.0f, out now);
                end = true;
                Ended();
            }
        }

        #endregion
    }
}
