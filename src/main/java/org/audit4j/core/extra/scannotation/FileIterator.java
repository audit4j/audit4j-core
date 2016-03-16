/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.extra.scannotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class FileIterator implements StreamIterator
{
   private final ArrayList files;
   private int index = 0;

   public FileIterator(File file, Filter filter)
   {
      files = new ArrayList();
      try
      {
         create(files, file, filter);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
    protected static void create(List list, File dir, Filter filter) throws Exception
    {
        create(list, dir, filter, dir.getCanonicalPath());
    }
   protected static void create(List list, File dir, Filter filter, String prefix) throws Exception
   {
      File[] files = dir.listFiles();
      for (int i = 0; i < files.length; i++)
      {
         if (files[i].isDirectory())
         {
            create(list, files[i], filter, prefix);
         }
         else
         {
             String path = files[i].getCanonicalPath();
             String relativePath = path.substring(prefix.length() + 1);
             if (File.separatorChar == '\\')
                 relativePath = relativePath.replace('\\', '/');
             if (filter == null || filter.accepts(relativePath))
            {
               list.add(files[i]);
            }
         }
      }
   }

   @Override
public InputStream next()
   {
      if (index >= files.size()) {
    	  return null;
      }
      File fp = (File) files.get(index++);
      try
      {
         return new FileInputStream(fp);
      }
      catch (FileNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   @Override
public void close()
   {
   }
}
