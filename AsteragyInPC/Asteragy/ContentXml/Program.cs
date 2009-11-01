using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework.Content.Pipeline.Serialization.Intermediate;
using System.Xml;

namespace ContentXml
{
	class Program
	{
		static void Main(string[] args)
		{
			using(XmlWriter writer = XmlWriter.Create(Console.Out)){
				IntermediateSerializer.Serialize<TimeSpan>(writer, TimeSpan.FromMilliseconds(1000), ".");
			}
			Console.ReadLine();
		}
	}
}
