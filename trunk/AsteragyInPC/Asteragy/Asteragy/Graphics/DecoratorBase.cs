using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;

namespace Asteragy.Graphics
{
    public abstract class DecoratorBase : IDecorator
    {
        protected IDecorator Child { get; private set; }

        #region IDecorator メンバ

        public void Initialize(IDecorator child)
        {
            Child = child;
            Initialize();
        }
        protected virtual void Initialize() { }

        public virtual IDecorator Update(GraphicsDevice device, GameTime gameTime)
        {
            Child = Child.Update(device, gameTime);
            return this;
        }

        public virtual void Draw(IAster unit, AsterDrawParameters parameters)
        {
            Child.Draw(unit, parameters);
        }

        #endregion
    }
}
