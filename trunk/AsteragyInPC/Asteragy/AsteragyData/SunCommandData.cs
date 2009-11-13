using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;

namespace AsteragyData
{
    public class SunCommandData : ContentTypeReader<SunCommandData>, IRead
    {
        #region IRead メンバ

        public void Read(ContentReader reader)
        {
        }

        #endregion

        protected override SunCommandData Read(ContentReader input, SunCommandData existingInstance)
        {
            if (existingInstance == null)
            {
                existingInstance = new SunCommandData();
                existingInstance.Read(input);
            }
            return existingInstance;
        }
    }
}
