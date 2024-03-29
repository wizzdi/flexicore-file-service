package com.wizzdi.flexicore.file.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wizzdi.flexicore.file.model.FileResource;
import com.wizzdi.flexicore.file.model.ZipFile;
import com.wizzdi.flexicore.security.request.BasicPropertiesFilter;
import com.wizzdi.flexicore.security.request.PaginationFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZipFileToFileResourceFilter extends PaginationFilter {

	private BasicPropertiesFilter basicPropertiesFilter;
	private Set<String> fileResourcesIds=new HashSet<>();
	@JsonIgnore
	private List<FileResource> fileResources;

	private Set<String> zipFilesIds=new HashSet<>();
	@JsonIgnore
	private List<ZipFile> zipFiles;


	public BasicPropertiesFilter getBasicPropertiesFilter() {
		return basicPropertiesFilter;
	}

	public <T extends ZipFileToFileResourceFilter> T setBasicPropertiesFilter(BasicPropertiesFilter basicPropertiesFilter) {
		this.basicPropertiesFilter = basicPropertiesFilter;
		return (T) this;
	}

	public Set<String> getFileResourcesIds() {
		return fileResourcesIds;
	}

	public <T extends ZipFileToFileResourceFilter> T setFileResourcesIds(Set<String> fileResourcesIds) {
		this.fileResourcesIds = fileResourcesIds;
		return (T) this;
	}

	@JsonIgnore
	public List<FileResource> getFileResources() {
		return fileResources;
	}

	public <T extends ZipFileToFileResourceFilter> T setFileResources(List<FileResource> fileResources) {
		this.fileResources = fileResources;
		return (T) this;
	}

	public Set<String> getZipFilesIds() {
		return zipFilesIds;
	}

	public <T extends ZipFileToFileResourceFilter> T setZipFilesIds(Set<String> zipFilesIds) {
		this.zipFilesIds = zipFilesIds;
		return (T) this;
	}

	@JsonIgnore
	public List<ZipFile> getZipFiles() {
		return zipFiles;
	}

	public <T extends ZipFileToFileResourceFilter> T setZipFiles(List<ZipFile> zipFiles) {
		this.zipFiles = zipFiles;
		return (T) this;
	}
}
