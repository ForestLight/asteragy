using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Asteragy.Graphics;
using Microsoft.Xna.Framework.Content;
using Asteragy.Graphics.Decorators;

namespace Asteragy.Game
{
    public class Aster
    {
        private static readonly Random random = new Random();
        private static Color[] colors;
        private static DefaultDecorator defaultDecorator;

        public static int Colors { get { return colors.Length; } }

        public static void Initialize(ContentManager content)
        {
            colors = content.Load<Color[]>("Datas/Aster/colors");
            defaultDecorator = new DefaultDecorator(content);
        }

        private IDecorator decorator;

        public AsterClass Type { get; set; }
        public PlayerType Owner { get; set; }
        public IDecorator Decorator
        {
            set
            {
                value.Initialize(decorator);
                decorator = value;
            }
        }
        public int Actions { get; set; }
        public int Color { get; set; }
        public bool Flag { get; set; }

        public Aster(AsterClass type)
        {
            Type = type;
            Color = random.Next(colors.Length);
            decorator = defaultDecorator.Make();
        }

        public void Update(GraphicsDevice device, GameTime gameTime)
        {
            decorator = decorator.Update(device, gameTime);
        }

        public void Draw(GraphicsDevice device, SpriteBatch sprite, Vector2 position)
        {
            AsterDrawParameters parameters = new AsterDrawParameters()
            {
                Device = device,
                Sprite = sprite,
                Position = position,
                Color = colors[Color],
                Origin = Type.Center,
                Effect = Owner == PlayerType.Two ? SpriteEffects.FlipVertically : SpriteEffects.None
            };
            decorator.Draw(Type, parameters);
        }
        public void OverDraw(GraphicsDevice device, SpriteBatch sprite, Vector2 position)
        {
        }
    }
}
