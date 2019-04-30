<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TrackMy.Build</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="/css/main.css">
		<link rel="stylesheet" href="/css/index.css">
	</head>
	<body>
		<div class="container py-2 mt-4 col-8 mx-auto bg-dark border border-secondary">
			<h2 id="header" class="text-center font-weight-bold">TrackMy.Build</h2>
			<h4 class="mt-2 text-center">See your character from level 1 to 100,</h4>
			<h4 class="text-center">with tracking for equipment, gems, and passives.</h4>
		</div>
		<a href="#" id="get-started" role="button" class="btn btn-secondary btn-small">Get Started!</a>
		<div id="sessid-input" class="bg-dark border border-light col-4">
			<span class="small font-weight-bold">Your POESESSID will not be saved on the server.</span>
			<form class="form-group form-row px-1 py-2" action="/setSessId" method="post">
				<input id="poesessid-input" type="text" class="form-control col-8" name="POESESSID" placeholder="POESESSID...">
				<input type="submit" class="ml-2 form-control btn btn-success btn-small col-3" value="Save">
			</form>
		</div>

		<div class="container py-2 mt-4 col-8 mx-auto bg-dark border border-secondary">
			<h3 class="text-center">How does it work?</h3>
			<p>Quite simply, you give a character name, and I periodically (more frequently at lower levels
			and less frequently at higher levels) download the named character's item and passive skill information
			from pathofexile.com. To do this, I require your <i>POESESSID</i> - but don't worry! This bit of information
			is not stored on the server; it remains safely stored in the cookies on your computer.</p>
			
			<p>From there, I interpret the data I receive from pathofexile.com, map it out to an equipment set,
			check if any of the equipment has changed since the last set of gathered data, and chart the changes
			over time. The progression is easily laid out by level, and gives what piece of equipment you changed,
			(still with full stats) and the piece of equipment it was changed to.</p>
			
			<h5 class="text-center">How do I find my POESESSID?</h5>
			<p>
				This information comes from your cookies on the pathofexile.com website. To find your POESESSID:
				<ol>
					<li>Log in to pathofexile.com</li>
					<li>With pathofexile.com as your active site, open the developer console in your browser. This
					is most often the F12 key, or by right-click and select Inspect/Inspect Element</li>
					<li>Navigate to the tab for cookie storage - on Firefox this is under "Storage", on Chrome this is
					under "Application".</li>
					<li>On the left, select the cookie for pathofexile.com, then on the right copy the string of
					characters under "Value" from the row named "POESESSID".</li>
					<li>Click Get Started and paste your POESESSID in the text box! (...then click Save)</li>
				</ol>
				It is important to note that your POESESSID will change when you log in from a new computer
				or when you are logged out of pathofexile.com and log back in. If you're encountering trouble
				looking up your characters or account, try inputting your (possibly new) POESESSID again.
			</p>
		</div>
		<div class="container py-2 mt-4 col-8 mx-auto bg-dark border border-secondary text-center">
			<h3 class="text-center">Why would I use it?</h3>
			<p>
				There are a variety of reasons!
				<ul class="list-group col-6 mx-auto my-2">
					<li class="list-group-item list-group-item-secondary">
						Feel squishier than a few levels ago? Look back and see what gear changes might have
						affected your survivability.
					</li>	
					<li class="list-group-item list-group-item-secondary">
						Do you race, and you want to analyze your itemization and passives
						from your most recent race event?
					</li> 
					<li class="list-group-item list-group-item-secondary">
						Do you write build guides, and want a quick tool to follow the growth of the build?
					</li>				
					<li class="list-group-item list-group-item-secondary">
						Do you find it interesting to get a glimpse of your character at level 23, after
						you've made it to level 100?
					</li>
				</ul>
				Why you use this tool is up to you - I just make it available for whatever use you may find.
			</p>
		</div>

		<div class="container py-2 mt-4 col-8 mx-auto bg-dark border border-secondary text-center">
			<h3 class="text-center">Hey, you didn't catch this weapon I wore for 10 seconds!</h3>
			<p>
				Unfortunately the abilities of this companion app are limited by the abilities
				of pathofexile.com's character info lookups. To the best of my ability to tell,
				a character's information only gets a forced update in limited circumstances.
				A good way to force an information update is to change zones (even just go from
				town to hideout, or hideout to town) and refresh the page to commit the change
				to memory.
			</p>
			<p>
				One other issue in tracking gear is when more than one swap happens on the same
				item slot before character information is updated. Given that gear worn for such
				a short duration (ie: change weapons a second time while still in town) is not
				typically considered part of a build, the decision was made that these items are
				safe to miss.
			</p>
		</div>		
		
		<script src="https://code.jquery.com/jquery-3.4.0.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
		<script src="/js/index.js"></script>
	</body>
</html>