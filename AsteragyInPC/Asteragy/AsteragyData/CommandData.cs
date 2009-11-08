using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework;

namespace AsteragyData
{
    public class CommandData : ContentTypeReader<CommandData>, IRead
    {
        public TimeSpan moveTime;
        public Texture2D CommandTexture;
        public Vector2 CommandCenter;
        public Texture2D CursorTexture;
        public Vector2 CursorCenter;

        #region IRead メンバ

        public void Read(ContentReader reader)
        {
        }

        #endregion

        protected override CommandData Read(ContentReader input, CommandData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new CommandData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
