using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using AsteragyData;
using Microsoft.Xna.Framework.Content;

namespace Asteragy.Graphics.Decorators
{
    public class DefaultDecorator : IDecorator, IPrototype<DefaultDecorator>
    {
        private static readonly Random random = new Random();
        private DefaultDecoratorData data;
        private TimeSpan time;
        private Vector2 to;
        private Vector2 from;
        private Vector2 now;

        public DefaultDecorator(ContentManager content)
        {
            data = content.Load<DefaultDecoratorData>("Datas/DefaultDecorator");
        }
        private DefaultDecorator(DefaultDecoratorData data)
        {
            this.data = data;
            next(out to);
        }

        #region IDecorator メンバ

        public void Initialize(IDecorator child)
        {
        }

        private void next(out Vector2 next)
        {
            next = new Vector2((float)random.NextDouble(), (float)random.NextDouble()) * data.Range;
        }

        public IDecorator Update(GraphicsDevice device, GameTime gameTime)
        {
            if (time < data.UnitTime)
            {
                Vector2.Lerp(ref from, ref to, (float)(time.TotalMilliseconds / data.UnitTime.TotalMilliseconds), out now);
            }
            else
            {
                from = to;
                next(out to);
                time = TimeSpan.Zero;
            }
            time += gameTime.ElapsedGameTime;
            return this;
        }

        public void Draw(IAster unit, AsterDrawParameters parameters)
        {
            parameters.Position += now;
            unit.Draw(parameters);
        }

        #endregion

        #region IPrototype<DefaultDecorator> メンバ

        public DefaultDecorator Make()
        {
            return new DefaultDecorator(data);
        }

        #endregion
    }
}
