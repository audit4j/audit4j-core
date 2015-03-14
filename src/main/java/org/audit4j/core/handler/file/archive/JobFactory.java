/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
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

package org.audit4j.core.handler.file.archive;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.util.annotation.Beeta;

@Beeta
public class JobFactory {

    public List<AbstractArchiveJob> getJobs(ArchiveEnv env){
        List<AbstractArchiveJob> jobs = new ArrayList<>();
        for (ArchiveType type : env.getArchiveTypes()) {
            if (type.equals(ArchiveType.LOCAL)) {
                jobs.add(new FileArchiveJob());
            } else if(type.equals(ArchiveType.FTP)){
                jobs.add(new FTPArchiveJob());
            }
        }
        return jobs;
    }
}
