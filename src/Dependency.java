import org.apache.tools.ant.Task;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Dependency extends Task {
    public String groupIdText;
    public String artifactIdText;
    public String versionText;
    String repo = "https://repo1.maven.org/maven2/";
    List<GroupId> groupIds = new ArrayList<GroupId>();
    List<ArtifactId> artifactIds = new ArrayList<ArtifactId>();
    List<Version> versions = new ArrayList<Version>();

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public GroupId createGroupId() {
        GroupId groupId = new GroupId();
        groupIds.add(groupId);
        return groupId;
    }

    public ArtifactId createArtifactId() {
        ArtifactId artifactId = new ArtifactId();
        artifactIds.add(artifactId);
        return artifactId;
    }

    public Version createVersion() {
        Version version = new Version();
        versions.add(version);
        return version;
    }

    public void execute() {
        getLibInfo();
        String name = getName();
        String fileName = "C:\\Users\\TD\\antLibs\\" + name;
        File sourceFile = new File(fileName);
        if (sourceFile.exists()) {
            addFromLocalDir(name, sourceFile);
            return;
        }
        String path = getPath(name);
        try {
            downloadLib(path, fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addFromLocalDir(String name, File sourceFile) {
        File destinationFile = new File("C:\\Users\\TD\\IdeaProjects\\AntTest1\\lib\\" + name);

        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             FileOutputStream fileOutputStream = new FileOutputStream(
                     destinationFile)) {

            int bufferSize;
            byte[] bufffer = new byte[512];
            while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                fileOutputStream.write(bufffer, 0, bufferSize);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPath(String name) {
        StringBuilder path = new StringBuilder(repo);
        String[] group = groupIdText.split("\\.");
        for (String g : group) {
            path.append(g).append("/");
        }
        path.append(artifactIdText).append("/");
        path.append(versionText).append("/");
        path.append(name);
        return path.toString();
    }

    private String getName() {
        StringBuilder name = new StringBuilder();
        return name.append(artifactIdText).append("-").append(versionText).append(".jar").toString();
    }

    private void downloadLib(String fileUrl, String fileName) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void getLibInfo() {
        for (GroupId groupId : groupIds) {
            groupIdText = groupId.message;
        }

        for (ArtifactId artifactId : artifactIds) {
            artifactIdText = artifactId.message;
        }

        for (Version version : versions) {
            versionText = version.message;
        }
    }
}
