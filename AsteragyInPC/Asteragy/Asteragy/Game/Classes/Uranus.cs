using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content;
using AsteragyData;

namespace Asteragy.Game.Classes
{
    public class Uranus : AsterClass
    {
        public Uranus(ContentManager content)
            : base(content.Load<AsterClassData>("Datas/Classes/uranus"))
        {
        }
    }
}
