//
//  ViewController.m
//  geobaseIOS
//
//  Created by Tillmann Heigel on 19.06.13.
//  Copyright (c) 2013 Greenmobile Innovations. All rights reserved.
//

#import "ViewController.h"
#import "MapsObject.h"
#import "Parser.h"
#import "MapViewController.h"
#import <UIColor+Colours.h>

@interface ViewController () <UITableViewDataSource, UITableViewDelegate>

 {
    NSArray* mapObjects;
}

@end

@implementation ViewController  

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    //get MapsObjects
    Parser *myParser = [[Parser alloc]init];
    mapObjects = [myParser getAllMapsObjects];
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)viewWillAppear:(BOOL)animated{
    //layout
    self.navigationController.navigationBar.tintColor = [UIColor chartreuseColor];
    self.title = @"gmino - geoengine.de";
}

#pragma UITableViewMethods

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return [mapObjects count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *MyIdentifier = @"MapObjectCell";
	
	// Try to retrieve from the table view a now-unused cell with the given identifier.
	UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:MyIdentifier];
	
	// If no cell is available, create a new one using the given identifier.
	if (cell == nil) {
		// Use the default cell style.
		cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:MyIdentifier];
	}
	
	// Set up the cell.
	MapsObject *myCurrentMapObject= [mapObjects objectAtIndex:indexPath.row];
	cell.textLabel.text = myCurrentMapObject.name;
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    
	return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    MapsObject *myCurrentMapObject= [mapObjects objectAtIndex:indexPath.row];
    
    MapViewController *myMapViewController = [[MapViewController alloc] initWithMapsObject:myCurrentMapObject];
    
    [self.navigationController pushViewController:myMapViewController animated:YES];
    
}


@end
