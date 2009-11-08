using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using AsteragyData;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics.Animations;

namespace Asteragy.Graphics.Decorators
{
    public class DefaultDecorator : IDecorator, IPrototype<DefaultDecorator>
    {
        private static readonly Random random = new Random();
        private DefaultDecoratorData data;
        private InterpolateOnce<Vector2> interpolate;

        public DefaultDecorator(ContentManager content)
        {
            data = content.Load<DefaultDecoratorData>("Datas/DefaultDecorator");
        }
        private DefaultDecorator(DefaultDecoratorData data)
        {
            this.data = data;
            interpolate = new InterpolateOnce<Vector2>(Vector2.Lerp, data.UnitTime, Vector2.Zero, next());
        }

        #region IDecorator メンバ

        public void Initialize(IDecorator child)
        {
        }

        private Vector2 next()
        {
            return new Vector2((float)random.NextDouble(), (float)random.NextDouble()) * data.Range;
        }

        public IDecorator Update(GraphicsDevice device, GameTime gameTime)
        {
            interpolate.Update(device, gameTime);
            if (interpolate.End)
                interpolate.Restart(interpolate.To, next());
            return this;
        }

        public void Draw(IAster unit, AsterDrawParameters parameters)
        {
            parameters.Position += interpolate.Now;
            unit.Draw(parameters);
        }

        public void OverDraw(OverDrawParameters parameters) { }

        #endregion

        #region IPrototype<DefaultDecorator> メンバ

        public DefaultDecorator Make()
        {
            return new DefaultDecorator(data);
        }

        #endregion
    }
}
